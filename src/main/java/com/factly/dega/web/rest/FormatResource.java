package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.config.Constants;
import com.factly.dega.service.FormatService;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.factly.dega.service.dto.FormatDTO;
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
 * REST controller for managing Format.
 */
@RestController
@RequestMapping("/api")
public class FormatResource {

    private final Logger log = LoggerFactory.getLogger(FormatResource.class);

    private static final String ENTITY_NAME = "coreFormat";

    private final FormatService formatService;

    public FormatResource(FormatService formatService) {
        this.formatService = formatService;
    }

    /**
     * POST  /formats : Create a new format.
     *
     * @param formatDTO the formatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formatDTO, or with status 400 (Bad Request) if the format has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/formats")
    @Timed
    public ResponseEntity<FormatDTO> createFormat(@Valid @RequestBody FormatDTO formatDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Format : {}", formatDTO);
        if (formatDTO.getId() != null) {
            throw new BadRequestAlertException("A new format cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (formatDTO.isIsDefault()) {
            formatDTO.setClientId(Constants.DEFAULT_CLIENTID);
        } else if (formatDTO.getClientId() == null) {
            Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
            if (obj != null) {
                formatDTO.setClientId((String) obj);
            }
        }
        formatDTO.setCreatedDate(ZonedDateTime.now());
        formatDTO.setLastUpdatedDate(ZonedDateTime.now());
        FormatDTO result = formatService.save(formatDTO);
        return ResponseEntity.created(new URI("/api/formats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /formats : Updates an existing format.
     *
     * @param formatDTO the formatDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formatDTO,
     * or with status 400 (Bad Request) if the formatDTO is not valid,
     * or with status 500 (Internal Server Error) if the formatDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/formats")
    @Timed
    public ResponseEntity<FormatDTO> updateFormat(@Valid @RequestBody FormatDTO formatDTO) throws URISyntaxException {
        log.debug("REST request to update Format : {}", formatDTO);
        if (formatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        formatDTO.setLastUpdatedDate(ZonedDateTime.now());
        FormatDTO result = formatService.save(formatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formatDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /formats : get all the formats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formats in body
     */
    @GetMapping("/formats")
    @Timed
    public ResponseEntity<List<FormatDTO>> getAllFormats(Pageable pageable) {
        log.debug("REST request to get a page of Formats");
        Page<FormatDTO> page = formatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/formats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /formats/:id : get the "id" format.
     *
     * @param id the id of the formatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/formats/{id}")
    @Timed
    public ResponseEntity<FormatDTO> getFormat(@PathVariable String id) {
        log.debug("REST request to get Format : {}", id);
        Optional<FormatDTO> formatDTO = formatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formatDTO);
    }

    /**
     * DELETE  /formats/:id : delete the "id" format.
     *
     * @param id the id of the formatDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/formats/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormat(@PathVariable String id) {
        log.debug("REST request to delete Format : {}", id);
        formatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/formats?query=:query : search for the format corresponding
     * to the query.
     *
     * @param query the query of the format search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/formats")
    @Timed
    public ResponseEntity<List<FormatDTO>> searchFormats(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Formats for query {}", query);
        Page<FormatDTO> page = formatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/formats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /formatbyslug/:slug : get the format.
     *
     * @param slug the slug of the FormatDTO
     * @param request
     * @return Optional<FormatDTO> format by clientId and slug
     */
    @GetMapping("/formatbyslug/{slug}")
    @Timed
    public Optional<FormatDTO> getFormatBySlug(@PathVariable String slug, HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
        String clientId = null;
        if (obj != null) {
            clientId = (String) obj;
        }
        log.debug("REST request to get Format by clienId : {} and slug : {}", clientId, slug);
        Optional<FormatDTO> formatDTO = formatService.findByClientIdAndSlug(clientId, slug);
        return formatDTO;
    }

}
