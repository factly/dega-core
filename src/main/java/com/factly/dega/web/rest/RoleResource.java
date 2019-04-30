package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.config.Constants;
import com.factly.dega.service.RoleService;
import com.factly.dega.service.dto.DegaUserDTO;
import com.factly.dega.service.dto.KeyCloakRoleDTO;
import com.factly.dega.service.dto.KeyCloakUserDTO;
import com.factly.dega.utils.KeycloakUtils;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.CommonUtil;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.factly.dega.service.dto.RoleDTO;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Role.
 */
@RestController
@RequestMapping("/api")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    private static final String ENTITY_NAME = "coreRole";

    private final RoleService roleService;

    private KeycloakUtils keycloakUtils;

    public RoleResource(RoleService roleService,
                        KeycloakUtils keycloakUtils) {
        this.roleService = roleService;
        this.keycloakUtils = keycloakUtils;
    }

    /**
     * POST  /roles : Create a new role.
     *
     * @param roleDTO the roleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleDTO, or with status 400 (Bad Request) if the role has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/roles")
    @Timed
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Role : {}", roleDTO);
        if (roleDTO.getId() != null) {
            throw new BadRequestAlertException("A new role cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (roleDTO.isIsDefault()) {
            roleDTO.setClientId(Constants.DEFAULT_CLIENTID);
        } else if (roleDTO.getClientId() == null) {
            Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
            if (obj != null) {
                roleDTO.setClientId((String) obj);
            }
        }
        roleDTO.setSlug(getSlug(roleDTO.getClientId(), CommonUtil.removeSpecialCharsFromString(roleDTO.getName())));
        roleDTO.setCreatedDate(ZonedDateTime.now());
        roleDTO.setLastUpdatedDate(ZonedDateTime.now());

        // save the user to keycloak
        JsonObject jObj = transformDTO(roleDTO);
        String jsonAsString = jObj.toString();
        Boolean status = keycloakUtils.create("roles", jsonAsString);
        if(status == false) {
            return null;
        }


        RoleDTO result = roleService.save(roleDTO);
        return ResponseEntity.created(new URI("/api/roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    private JsonObject transformDTO(RoleDTO roleDTO) {
        KeyCloakRoleDTO keyCloakRoleDTO = new KeyCloakRoleDTO(
            String.valueOf(java.util.UUID.randomUUID()),
            roleDTO.getName(),
            roleDTO.toString(),
            false,
            false,
            roleDTO.getClientId()
        );
        JsonObject jObj = (JsonObject)new GsonBuilder().create().toJsonTree(keyCloakRoleDTO);
        return jObj;
    }

    /**
     * PUT  /roles : Updates an existing role.
     *
     * @param roleDTO the roleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleDTO,
     * or with status 400 (Bad Request) if the roleDTO is not valid,
     * or with status 500 (Internal Server Error) if the roleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/roles")
    @Timed
    public ResponseEntity<RoleDTO> updateRole(@Valid @RequestBody RoleDTO roleDTO) throws URISyntaxException {
        log.debug("REST request to update Role : {}", roleDTO);
        if (roleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        roleDTO.setLastUpdatedDate(ZonedDateTime.now());
        RoleDTO result = roleService.save(roleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /roles : get all the roles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roles in body
     */
    @GetMapping("/roles")
    @Timed
    public ResponseEntity<List<RoleDTO>> getAllRoles(Pageable pageable) {
        log.debug("REST request to get a page of Roles");
        Page<RoleDTO> page = roleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/roles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /roles/:id : get the "id" role.
     *
     * @param id the id of the roleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/roles/{id}")
    @Timed
    public ResponseEntity<RoleDTO> getRole(@PathVariable String id) {
        log.debug("REST request to get Role : {}", id);
        Optional<RoleDTO> roleDTO = roleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleDTO);
    }

    /**
     * DELETE  /roles/:id : delete the "id" role.
     *
     * @param id the id of the roleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/roles/{id}")
    @Timed
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        log.debug("REST request to delete Role : {}", id);
        roleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/roles?query=:query : search for the role corresponding
     * to the query.
     *
     * @param query the query of the role search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/roles")
    @Timed
    public ResponseEntity<List<RoleDTO>> searchRoles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Roles for query {}", query);
        Page<RoleDTO> page = roleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/roles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rolebyslug/:slug : get the role.
     *
     * @param slug the slug of the RoleDTO
     * @return Optional<RoleDTO> role by clientId and slug
     */
    @GetMapping("/rolebyslug/{slug}")
    @Timed
    public Optional<RoleDTO> getRoleBySlug(@PathVariable String slug, HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
        String clientId = null;
        if (obj != null) {
            clientId = (String) obj;
        }
        log.debug("REST request to get Role by clienId : {} and slug : {}", clientId, slug);
        Optional<RoleDTO> roleDTO = roleService.findByClientIdAndSlug(clientId, slug);
        return roleDTO;
    }

    public String getSlug(String clientId, String name){
        if(clientId != null && name != null){
            int slugExtention = 0;
            return createSlug(clientId, name, name, slugExtention);
        }
        return null;
    }

    public String createSlug(String clientId, String slug, String tempSlug, int slugExtention){
        Optional<RoleDTO> roleDTO = roleService.findByClientIdAndSlug(clientId, slug);
        if(roleDTO.isPresent()){
            slugExtention += 1;
            slug = tempSlug + slugExtention;
            return createSlug(clientId, slug, tempSlug, slugExtention);
        }
        return slug;
    }

}
