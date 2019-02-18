package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.config.Constants;
import com.factly.dega.service.MediaService;
import com.factly.dega.service.impl.FileStorageService;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.CommonUtil;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.factly.dega.service.dto.MediaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Media.
 */
@RestController
@RequestMapping("/api")
public class MediaResource {

    private final Logger log = LoggerFactory.getLogger(MediaResource.class);

    private static final String ENTITY_NAME = "coreMedia";

    private final MediaService mediaService;

    @Autowired
    private FileStorageService fileStorageService;

    public MediaResource(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * POST  /media : Create a new media.
     *
     * @param mediaDTO the mediaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mediaDTO, or with status 400 (Bad Request) if the media has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/media")
    @Timed
    public ResponseEntity<MediaDTO> createMedia(@Valid @RequestBody MediaDTO mediaDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Media : {}", mediaDTO);
        if (mediaDTO.getId() != null) {
            throw new BadRequestAlertException("A new media cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Object obj = request.getAttribute(Constants.CLIENT_ID);
        if (obj != null) {
            mediaDTO.setClientId((String) obj);
        }
        mediaDTO.setCreatedDate(ZonedDateTime.now());
        mediaDTO.setLastUpdatedDate(ZonedDateTime.now());
        MediaDTO result = mediaService.save(mediaDTO);
        return ResponseEntity.created(new URI("/api/media/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /media : Create a new media.
     *
     * @param file the media to be uploaded
     * @return the ResponseEntity with status 201 (Created) and with body the new mediaDTO, or with status 400 (Bad Request) if the media has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @RequestMapping(value = "/media/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<MediaDTO> uploadMedia(@RequestParam("file") @Valid @NotNull @NotBlank MultipartFile file,
                                                HttpServletRequest request) throws URISyntaxException {
        MediaDTO mediaDTO = new MediaDTO();
        log.debug("REST request to save Media : {}", mediaDTO);
        Object client = request.getAttribute(Constants.CLIENT_ID);

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        int month = localDate.getMonthValue();
        String fileName = fileStorageService.storeFile(file, client, year, month);
        mediaDTO.setName(fileName);

        // set the default slug by removing all special chars except letters and numbers
        mediaDTO.setSlug(getSlug((String) client, fileName));
        mediaDTO.setTitle(fileName.substring(0, fileName.lastIndexOf('.')));

        String fileSep = System.getProperty("file.separator");
        String clientStr = (String) client;
        String filePath = fileSep + "dega-content" + fileSep + clientStr + fileSep + year + fileSep + month + fileSep;
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(filePath)
            .path(fileName)
            .toUriString();
        mediaDTO.setUrl(fileDownloadUri);
        mediaDTO.setSlug(fileDownloadUri);

        Object user = request.getAttribute(Constants.USER_ID);
        if (user != null) {
            mediaDTO.setUploadedBy((String) user);
        }

        Long fileSize = file.getSize();
        mediaDTO.setFileSize(fileSize.toString());
        mediaDTO.setType(file.getContentType());

        if (mediaDTO.getId() != null) {
            throw new BadRequestAlertException("A new media cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (client != null) {
            mediaDTO.setClientId((String) client);
        }
        mediaDTO.setCreatedDate(ZonedDateTime.now());
        mediaDTO.setLastUpdatedDate(ZonedDateTime.now());

        // set it to current date and guve user an option to edit it later
        mediaDTO.setPublishedDate(ZonedDateTime.now());
        MediaDTO result = mediaService.save(mediaDTO);
        return ResponseEntity.created(new URI("/api/media/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media : Updates an existing media.
     *
     * @param mediaDTO the mediaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mediaDTO,
     * or with status 400 (Bad Request) if the mediaDTO is not valid,
     * or with status 500 (Internal Server Error) if the mediaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/media")
    @Timed
    public ResponseEntity<MediaDTO> updateMedia(@Valid @RequestBody MediaDTO mediaDTO) throws URISyntaxException {
        log.debug("REST request to update Media : {}", mediaDTO);
        if (mediaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        mediaDTO.setLastUpdatedDate(ZonedDateTime.now());
        MediaDTO result = mediaService.save(mediaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mediaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media : get all the media.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of media in body
     */
    @GetMapping("/media")
    @Timed
    public ResponseEntity<List<MediaDTO>> getAllMedia(Pageable pageable, HttpServletRequest request) {
        log.debug("REST request to get a page of Media");
        Page<MediaDTO> page = new PageImpl<>(new ArrayList<>());
        Object obj = request.getAttribute(Constants.CLIENT_ID);
        if (obj != null) {
            String clientId = (String) obj;
            page = mediaService.findByClientId(clientId, pageable);

        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/media");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /media/:id : get the "id" media.
     *
     * @param id the id of the mediaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mediaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/media/{id}")
    @Timed
    public ResponseEntity<MediaDTO> getMedia(@PathVariable String id) {
        log.debug("REST request to get Media : {}", id);
        Optional<MediaDTO> mediaDTO = mediaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mediaDTO);
    }

    /**
     * DELETE  /media/:id : delete the "id" media.
     *
     * @param id the id of the mediaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/media/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedia(@PathVariable String id) {
        log.debug("REST request to delete Media : {}", id);
        mediaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/media?query=:query : search for the media corresponding
     * to the query.
     *
     * @param query the query of the media search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/media")
    @Timed
    public ResponseEntity<List<MediaDTO>> searchMedia(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Media for query {}", query);
        Page<MediaDTO> page = mediaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/media");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mediabyslug/:slug : get the media.
     *
     * @param slug the slug of the MediaDTO
     * @return Optional<MediaDTO> media by clientId and slug
     */
    @GetMapping("/mediabyslug/{slug}")
    @Timed
    public Optional<MediaDTO> getMediaBySlug(@PathVariable String slug, HttpServletRequest request) {
        Object obj = request.getAttribute(Constants.CLIENT_ID);
        String clientId = null;
        if (obj != null) {
            clientId = (String) obj;
        }
        log.debug("REST request to get media by clienId : {} and slug : {}", clientId, slug);
        Optional<MediaDTO> mediaDTO = mediaService.findByClientIdAndSlug(clientId, slug);
        return mediaDTO;
    }

    public String getSlug(String clientId, String claim){
        if(claim != null){
            int slugExtention = 0;
            String tempSlug = CommonUtil.removeSpecialCharsFromString(claim);
            return createSlug(clientId, tempSlug, tempSlug, slugExtention);
        }
        return null;
    }

    public String createSlug(String clientId, String slug, String tempSlug, int slugExtention){
        Optional<MediaDTO> mediaDTO = mediaService.findByClientIdAndSlug(clientId, slug);
        if(mediaDTO.isPresent()){
            slugExtention += 1;
            slug = tempSlug + slugExtention;
            return createSlug(clientId, slug, tempSlug, slugExtention);
        }
        return slug;
    }

}
