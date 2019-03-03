package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.config.Constants;
import com.factly.dega.service.StatusService;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.factly.dega.service.dto.StatusDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
 * REST controller for managing Status.
 */
@RestController
@RequestMapping("/api")
public class StatusResource {

    private final Logger log = LoggerFactory.getLogger(StatusResource.class);

    private static final String ENTITY_NAME = "coreStatus";

    private final StatusService statusService;

    public StatusResource(StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * POST  /statuses : Create a new status.
     *
     * @param statusDTO the statusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statusDTO, or with status 400 (Bad Request) if the status has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/statuses")
    @Timed
    public ResponseEntity<StatusDTO> createStatus(@Valid @RequestBody StatusDTO statusDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Status : {}", statusDTO);
        if (statusDTO.getId() != null) {
            throw new BadRequestAlertException("A new status cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (statusDTO.isIsDefault()) {
            statusDTO.setClientId(Constants.DEFAULT_CLIENTID);
        } else if (statusDTO.getClientId() == null) {
            Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
            if (obj != null) {
                statusDTO.setClientId((String) obj);
            }
        }
        statusDTO.setCreatedDate(ZonedDateTime.now());
        statusDTO.setLastUpdatedDate(ZonedDateTime.now());
        StatusDTO result = statusService.save(statusDTO);
        return ResponseEntity.created(new URI("/api/statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /statuses : Updates an existing status.
     *
     * @param statusDTO the statusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statusDTO,
     * or with status 400 (Bad Request) if the statusDTO is not valid,
     * or with status 500 (Internal Server Error) if the statusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/statuses")
    @Timed
    public ResponseEntity<StatusDTO> updateStatus(@Valid @RequestBody StatusDTO statusDTO) throws URISyntaxException {
        log.debug("REST request to update Status : {}", statusDTO);
        if (statusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        statusDTO.setLastUpdatedDate(ZonedDateTime.now());
        StatusDTO result = statusService.save(statusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statuses : get all the statuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statuses in body
     */
    @GetMapping("/statuses")
    @Timed
    public ResponseEntity<List<StatusDTO>> getAllStatuses(Pageable pageable) {
        log.debug("REST request to get a page of Statuses");
        Page<StatusDTO> page = statusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statuses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /statuses/:id : get the "id" status.
     *
     * @param id the id of the statusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/statuses/{id}")
    @Timed
    public ResponseEntity<StatusDTO> getStatus(@PathVariable String id) {
        log.debug("REST request to get Status : {}", id);
        Optional<StatusDTO> statusDTO = statusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusDTO);
    }

    /**
     * DELETE  /statuses/:id : delete the "id" status.
     *
     * @param id the id of the statusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatus(@PathVariable String id) {
        log.debug("REST request to delete Status : {}", id);
        statusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/statuses?query=:query : search for the status corresponding
     * to the query.
     *
     * @param query the query of the status search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/statuses")
    @Timed
    public ResponseEntity<List<StatusDTO>> searchStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Statuses for query {}", query);
        Page<StatusDTO> page = statusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /statusbyslug/:slug : get the status.
     *
     * @param slug the slug of the StatusDTO
     * @return Optional<StatusDTO> status by clientId and slug
     */
    @GetMapping("/statusbyslug/{slug}")
    @Timed
    public Optional<StatusDTO> getStatusBySlug(@PathVariable String slug, HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
        String clientId = null;
        if (obj != null) {
            clientId = (String) obj;
        }
        log.debug("REST request to get Status by clienId : {} and slug : {}", clientId, slug);
        Optional<StatusDTO> statusDTO = statusService.findByClientIdAndSlug(clientId, slug);
        return statusDTO;
    }

}
