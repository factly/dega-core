package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.domain.RoleMapping;
import com.factly.dega.service.DegaUserService;
import com.factly.dega.service.OrganizationService;
import com.factly.dega.service.dto.*;
import com.factly.dega.utils.KeycloakUtils;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.CommonUtil;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DegaUser.
 */
@RestController
@RequestMapping("/api")
public class DegaUserResource {

    private final Logger log = LoggerFactory.getLogger(DegaUserResource.class);

    private static final String ENTITY_NAME = "coreDegaUser";

    private final DegaUserService degaUserService;

    private KeycloakUtils keycloakUtils;

    private final OrganizationService organizationService;

    @Autowired
    public DegaUserResource(
        DegaUserService degaUserService,
        RestTemplate restTemplate,
        KeycloakUtils keycloakUtils,
        OrganizationService organizationService) {
        this.degaUserService = degaUserService;
        this.keycloakUtils = keycloakUtils;
        this.organizationService = organizationService;
    }

    /**
     * POST  /dega-users : Create a new degaUser.
     *
     * @param degaUserDTO the degaUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new degaUserDTO, or with status 400 (Bad Request) if the degaUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dega-users")
    @Timed
    public ResponseEntity<DegaUserDTO> createDegaUser(@Valid @RequestBody DegaUserDTO degaUserDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save DegaUser : {}", degaUserDTO);
        if (degaUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new degaUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        degaUserDTO.setCreatedDate(ZonedDateTime.now());

        // save the user to keycloak
        JsonObject jObj = transformDTO(degaUserDTO);
        String jsonAsString = jObj.toString();
        Boolean status = keycloakUtils.create("users", jsonAsString);
        if(status == false) {
            throw new RuntimeException("Failed to create new keycloak user");
        }

        // get the keycloak id
        String keyCloakId = keycloakUtils.getUserId("users?username="+degaUserDTO.getEmail());
        if (keyCloakId.equals("NOT_FOUND")) {
            throw new RuntimeException("Failed to retrieve keycloak unique id");
        }
        degaUserDTO.setKeycloakId(keyCloakId);
        degaUserDTO.setSlug(getSlug(CommonUtil.removeSpecialCharsFromString(degaUserDTO.getDisplayName())));

        Boolean isSuperAdmin = degaUserDTO.isIsSuperAdmin();
        if (isSuperAdmin != null && isSuperAdmin == true) {
            boolean createStatus = addSuperAdminRole(keyCloakId);
            if (!createStatus) {
                throw new RuntimeException("Failed to add super admin role mapping to the keycloak user");
            }
        } else {
            // pull role mapping for this org
            Set<RoleMappingDTO> roleMappings = degaUserDTO.getRoleMappings();
            Set<RoleMappingDTO> currentOrgsRoleMappings = roleMappings
                .stream()
                .filter(rm -> !rm.getId().equals(degaUserDTO.getOrganizationCurrentId()))
                .collect(toSet());

            // transform dega dto to keycloak dto
            KeyCloakRoleMappingsDTO keyCloakRoleMappingsDTO = new KeyCloakRoleMappingsDTO();
            List<KeyCloakRoleMappingDTO> keyCloakOrgsRoleMappings =
                currentOrgsRoleMappings.stream().map(rm -> transformRoleMapping(rm)).collect(toList());
            keyCloakRoleMappingsDTO.setKeyCloakRoleMappingsDTO(keyCloakOrgsRoleMappings);

            // add new roles to keycloak
            String endPoint = "users/"+keyCloakId+"/role-mappings/realm";
            JsonObject jObj1 = (JsonObject)new GsonBuilder().create().toJsonTree(keyCloakRoleMappingsDTO);
            String roleMappingAsString = jObj1.get("keyCloakRoleMappingsDTO").toString();
            boolean createStatus = keycloakUtils.create(endPoint, roleMappingAsString);
            if (!createStatus) {
                throw new RuntimeException("Failed to apply current organization's role mappings on the keycloak user");
            }
        }

        // save the user to mongo database
        log.info("Keycloak user creation is successful, adding user to dega backend");
        DegaUserDTO result = degaUserService.save(degaUserDTO);
        return ResponseEntity.created(new URI("/api/dega-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dega-users : Updates an existing degaUser.
     *
     * @param degaUserDTO the degaUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated degaUserDTO,
     * or with status 400 (Bad Request) if the degaUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the degaUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dega-users")
    @Timed
    public ResponseEntity<DegaUserDTO> updateDegaUser(@Valid @RequestBody DegaUserDTO degaUserDTO) throws URISyntaxException {
        log.debug("REST request to update DegaUser : {}", degaUserDTO);
        if (degaUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        String keyCloakUserId = degaUserDTO.getKeycloakId();
        Boolean isSuperAdmin = degaUserDTO.isIsSuperAdmin();
        if (isSuperAdmin != null && isSuperAdmin == true) {
            boolean createStatus = addSuperAdminRole(keyCloakUserId);
            if (!createStatus) {
                throw new RuntimeException("Failed to add super admin role mapping to the keycloak user");
            }
        } else {
            // pull role mapping for this org
            Set<RoleMappingDTO> roleMappings = degaUserDTO.getRoleMappings();
            List<RoleMappingDTO> currentOrgRoleMappings = roleMappings
                .stream()
                .filter(rm -> rm.getOrganizationId().equals(degaUserDTO.getOrganizationCurrentId()))
                .collect(toList());
            List<KeyCloakRoleMappingDTO> keyCloakOrgsRoleMappings =
                currentOrgRoleMappings.stream().map(rm -> transformRoleMapping(rm)).collect(toList());

            // get current user role mapping and remove current dega role mappings (if any exists)
            String endPoint = "users/"+keyCloakUserId+"/role-mappings/realm";
            KeyCloakRoleMappingDTO[] roleMappingsDTO = keycloakUtils.getRoleMappingsDTO(endPoint);

            // Delete all the dega roles
            List<KeyCloakRoleMappingDTO> rolesToBeRemoved = Arrays.stream(roleMappingsDTO)
                .filter(rm -> rm.getDescription().startsWith("DEGA_ROLE"))
                .collect(toList());
            KeyCloakRoleMappingsDTO keyCloakRoleMappingsDTO1 = new KeyCloakRoleMappingsDTO();
            keyCloakRoleMappingsDTO1.setKeyCloakRoleMappingsDTO(rolesToBeRemoved);
            JsonObject jObj1 = (JsonObject)new GsonBuilder().create().toJsonTree(keyCloakRoleMappingsDTO1);
            String rolesToBeRemovedAsString = jObj1.get("keyCloakRoleMappingsDTO").toString();
            boolean deleteStatus = keycloakUtils.delete(endPoint, rolesToBeRemovedAsString);
            if (!deleteStatus) {
                throw new RuntimeException("Failed to delete existing dega roles on the keycloak user");
            }

            // Add new roles on the current org
            List<KeyCloakRoleMappingDTO> newRoles = Arrays.stream(roleMappingsDTO)
                .filter(rm -> !rm.getDescription().startsWith("DEGA_ROLE"))
                .collect(toList());
            newRoles.addAll(keyCloakOrgsRoleMappings);
            KeyCloakRoleMappingsDTO keyCloakRoleMappingsDTO2 = new KeyCloakRoleMappingsDTO();
            keyCloakRoleMappingsDTO2.setKeyCloakRoleMappingsDTO(newRoles);
            JsonObject jObj2 = (JsonObject)new GsonBuilder().create().toJsonTree(keyCloakRoleMappingsDTO2);
            String roleMappingAsString = jObj2.get("keyCloakRoleMappingsDTO").toString();
            boolean createStatus = keycloakUtils.create(endPoint, roleMappingAsString);
            if (!createStatus) {
                throw new RuntimeException("Failed to apply current organization's role mappings on the keycloak user");
            }
        }

        DegaUserDTO result = degaUserService.save(degaUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, degaUserDTO.getId().toString()))
            .body(result);
    }

    private boolean addSuperAdminRole(String keyCloakUserId) {
        // get keycloak role name and role id
        String endpointUrl = "roles/ROLE_SUPER_ADMIN";
        KeyCloakRoleMappingDTO keyCloakRoleMappingDTO = keycloakUtils.getRoleMapping(endpointUrl);
        List<KeyCloakRoleMappingDTO> roles = new ArrayList<>();
        roles.add(keyCloakRoleMappingDTO);
        KeyCloakRoleMappingsDTO keyCloakRoleMappingsDTO2 = new KeyCloakRoleMappingsDTO();
        keyCloakRoleMappingsDTO2.setKeyCloakRoleMappingsDTO(roles);
        JsonObject jObj = (JsonObject)new GsonBuilder().create().toJsonTree(keyCloakRoleMappingsDTO2);
        String roleMappingAsString = jObj.get("keyCloakRoleMappingsDTO").toString();
        String endPoint = "users/"+keyCloakUserId+"/role-mappings/realm";
        return keycloakUtils.create(endPoint, roleMappingAsString);
    }

    /**
     * GET  /dega-users : get all the degaUsers.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of degaUsers in body
     */
    @GetMapping("/dega-users")
    @Timed
    public ResponseEntity<List<DegaUserDTO>> getAllDegaUsers(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of DegaUsers");
        Page<DegaUserDTO> page;
        if (eagerload) {
            page = degaUserService.findAllWithEagerRelationships(pageable);
        } else {
            page = degaUserService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/dega-users?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dega-users/:id : get the "id" degaUser.
     *
     * @param id the id of the degaUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the degaUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dega-users/{id}")
    @Timed
    public ResponseEntity<DegaUserDTO> getDegaUser(@PathVariable String id) {
        log.debug("REST request to get DegaUser : {}", id);
        Optional<DegaUserDTO> degaUserDTO = degaUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(degaUserDTO);
    }

    /**
     * GET  /dega-users/:id : get the "id" degaUser.
     *
     * @param id the id of the degaUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the degaUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dega-users/{id}/organizations")
    @Timed
    public ResponseEntity<List<OrganizationDTO>> getDegaUserOrganizations(Pageable pageable, @PathVariable String id) {
        log.debug("REST request to get DegaUser : {}", id);
        Optional<DegaUserDTO> degaUserDTO = degaUserService.findOne(id);

        if (degaUserDTO.get() == null) {
            return null;
        }
        DegaUserDTO degaUser = degaUserDTO.get();
        Boolean isSuperAdmin = degaUser.isIsSuperAdmin();
        if (isSuperAdmin != null && isSuperAdmin == true) {
            // return all oprgs
            Page<OrganizationDTO> page = organizationService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizations");
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
            Set<RoleMappingDTO> roleMappings = degaUser.getRoleMappings();
            Set<OrganizationDTO> orgDTOs = new HashSet<>();
            roleMappings.stream().forEach(rm -> {
                OrganizationDTO organizationDTO = new OrganizationDTO();
                organizationDTO.setName(rm.getOrganizationName());
                organizationDTO.setId(rm.getOrganizationId());
                orgDTOs.add(organizationDTO);
            });
            List<OrganizationDTO> aList = orgDTOs.stream().collect(Collectors.toList());

            return ResponseEntity.ok().body(aList);
        }
    }

    /**
     * DELETE  /dega-users/:id : delete the "id" degaUser.
     *
     * @param id the id of the degaUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dega-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteDegaUser(@PathVariable String id) {
        log.debug("REST request to delete DegaUser : {}", id);
        degaUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/dega-users?query=:query : search for the degaUser corresponding
     * to the query.
     *
     * @param query the query of the degaUser search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dega-users")
    @Timed
    public ResponseEntity<List<DegaUserDTO>> searchDegaUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DegaUsers for query {}", query);
        Page<DegaUserDTO> page = degaUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dega-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dega-users/email/:email : get the "email" degaUser.
     *
     * @param email the email of the degaUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the degaUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dega-users/email/{email}")
    @Timed
    public ResponseEntity<DegaUserDTO> getDegaUserByEmail(@PathVariable String email) {
        log.debug("REST request to get DegaUser by Email : {}", email);
        Optional<DegaUserDTO> degaUserDTO = degaUserService.findByEmailId(email);
        return ResponseUtil.wrapOrNotFound(degaUserDTO);
    }

    private KeyCloakRoleMappingDTO transformRoleMapping(RoleMappingDTO roleMappingDTO) {
        KeyCloakRoleMappingDTO keyCloakRoleMappingDTO = new KeyCloakRoleMappingDTO();
        keyCloakRoleMappingDTO.setName(roleMappingDTO.getKeycloakName());
        keyCloakRoleMappingDTO.setId(roleMappingDTO.getKeycloakId());
        keyCloakRoleMappingDTO.setClientRole(false);
        keyCloakRoleMappingDTO.setComposite(false);
        keyCloakRoleMappingDTO.setContainerId("dega");
        return keyCloakRoleMappingDTO;
    }

    private JsonObject transformDTO(DegaUserDTO degaUserDTO) {
        KeyCloakUserDTO keyCloakUserDTO = new KeyCloakUserDTO(
            String.valueOf(java.util.UUID.randomUUID()),
            degaUserDTO.getFirstName(),
            degaUserDTO.getLastName(),
            degaUserDTO.isEnabled(),
            degaUserDTO.isEmailVerified(),
            degaUserDTO.getEmail(),
            degaUserDTO.getEmail()
        );
        JsonObject jObj = (JsonObject)new GsonBuilder().create().toJsonTree(keyCloakUserDTO);
        return jObj;
    }

    public String getSlug(String name){
        if(name != null){
            int slugExtention = 0;
            return createSlug(name, name, slugExtention);
        }
        return null;
    }

    public String createSlug(String slug, String tempSlug, int slugExtention){
        Optional<DegaUserDTO> postDTO = degaUserService.findBySlug(slug);
        if(postDTO.isPresent()){
            slugExtention += 1;
            slug = tempSlug + slugExtention;
            return createSlug(slug, tempSlug, slugExtention);
        }
        return slug;
    }
}
