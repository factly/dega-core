package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.service.RoleMappingService;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.factly.dega.service.dto.RoleMappingDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RoleMapping.
 */
@RestController
@RequestMapping("/api")
public class RoleMappingResource {

    private final Logger log = LoggerFactory.getLogger(RoleMappingResource.class);

    private static final String ENTITY_NAME = "coreRoleMapping";

    private final RoleMappingService roleMappingService;

    public RoleMappingResource(RoleMappingService roleMappingService) {
        this.roleMappingService = roleMappingService;
    }

    /**
     * POST  /role-mappings : Create a new roleMapping.
     *
     * @param roleMappingDTO the roleMappingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleMappingDTO, or with status 400 (Bad Request) if the roleMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/role-mappings")
    @Timed
    public ResponseEntity<RoleMappingDTO> createRoleMapping(@Valid @RequestBody RoleMappingDTO roleMappingDTO) throws URISyntaxException {
        log.debug("REST request to save RoleMapping : {}", roleMappingDTO);
        if (roleMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleMappingDTO result = roleMappingService.save(roleMappingDTO);
        return ResponseEntity.created(new URI("/api/role-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /role-mappings : Updates an existing roleMapping.
     *
     * @param roleMappingDTO the roleMappingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleMappingDTO,
     * or with status 400 (Bad Request) if the roleMappingDTO is not valid,
     * or with status 500 (Internal Server Error) if the roleMappingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/role-mappings")
    @Timed
    public ResponseEntity<RoleMappingDTO> updateRoleMapping(@Valid @RequestBody RoleMappingDTO roleMappingDTO) throws URISyntaxException {
        log.debug("REST request to update RoleMapping : {}", roleMappingDTO);
        if (roleMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleMappingDTO result = roleMappingService.save(roleMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roleMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /role-mappings : get all the roleMappings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roleMappings in body
     */
    @GetMapping("/role-mappings")
    @Timed
    public ResponseEntity<List<RoleMappingDTO>> getAllRoleMappings(Pageable pageable) {
        log.debug("REST request to get a page of RoleMappings");
        Page<RoleMappingDTO> page = roleMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/role-mappings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /role-mappings/:id : get the "id" roleMapping.
     *
     * @param id the id of the roleMappingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/role-mappings/{id}")
    @Timed
    public ResponseEntity<RoleMappingDTO> getRoleMapping(@PathVariable String id) {
        log.debug("REST request to get RoleMapping : {}", id);
        Optional<RoleMappingDTO> roleMappingDTO = roleMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleMappingDTO);
    }

    /**
     * DELETE  /role-mappings/:id : delete the "id" roleMapping.
     *
     * @param id the id of the roleMappingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/role-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoleMapping(@PathVariable String id) {
        log.debug("REST request to delete RoleMapping : {}", id);
        roleMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/role-mappings?query=:query : search for the roleMapping corresponding
     * to the query.
     *
     * @param query the query of the roleMapping search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/role-mappings")
    @Timed
    public ResponseEntity<List<RoleMappingDTO>> searchRoleMappings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RoleMappings for query {}", query);
        Page<RoleMappingDTO> page = roleMappingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/role-mappings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
