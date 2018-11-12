package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.service.DegaUserService;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.factly.dega.service.dto.DegaUserDTO;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DegaUser.
 */
@RestController
@RequestMapping("/api")
public class DegaUserResource {

    private final Logger log = LoggerFactory.getLogger(DegaUserResource.class);

    private static final String ENTITY_NAME = "coreDegaUser";

    private final DegaUserService degaUserService;

    private final RestTemplate restTemplate;

    private String keycloakServerURI;

    public DegaUserResource(DegaUserService degaUserService, RestTemplate restTemplate, @Value("${keycloak.api.uri}") String keycloakServerURI) {
        this.degaUserService = degaUserService;
        this.restTemplate = restTemplate;
        this.keycloakServerURI = keycloakServerURI;
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
        DegaUserDTO result = degaUserService.save(degaUserDTO);

        // create new user in keycloak
        OAuth2Authentication auth = (OAuth2Authentication) request.getUserPrincipal();
        if (auth != null) {
            String token = "Bearer " + (OAuth2AuthenticationDetails.class.cast(auth.getDetails())).getTokenValue();
            JsonObject jObj = transformDTO(degaUserDTO);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.add("Authorization", token);
            String jsonAsString = jObj.toString();
            HttpEntity<String> httpEntity = new HttpEntity(jsonAsString, httpHeaders);
            restTemplate.postForObject(keycloakServerURI, httpEntity, String.class);
        }

        return ResponseEntity.created(new URI("/api/dega-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    private JsonObject transformDTO(DegaUserDTO degaUserDTO) {
        JsonObject jObj = (JsonObject)new GsonBuilder().create().toJsonTree(degaUserDTO);

        jObj.remove("facebookURL");
        jObj.remove("isActive");
        jObj.remove("displayName");
        jObj.remove("slug");
        jObj.remove("roleId");
        jObj.remove("organizations");
        jObj.remove("organizationDefaultId");
        jObj.remove("organizationCurrentId");
        jObj.remove("organizationCurrentName");
        jObj.remove("roleName");
        jObj.remove("description");
        jObj.remove("profilePicture");
        jObj.remove("githubURL");
        jObj.remove("linkedinURL");
        jObj.remove("instagramURL");
        jObj.remove("twitterURL");
        jObj.remove("facebookURL");
        jObj.remove("website");

        jObj.addProperty("username", degaUserDTO.getEmail());
        jObj.addProperty("id", String.valueOf(java.util.UUID.randomUUID()));

        // TODO: Add these attributes in dega user

        // jObj.addProperty("enabled", degaUserDTO.isEnabled());
        // jObj.addProperty("emailVerified", true);

        return jObj;
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
        DegaUserDTO result = degaUserService.save(degaUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, degaUserDTO.getId().toString()))
            .body(result);
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

}
