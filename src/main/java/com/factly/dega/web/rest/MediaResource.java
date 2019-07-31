package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.config.Constants;
import com.factly.dega.domain.MediaUrls;
import com.factly.dega.service.MediaService;
import com.factly.dega.service.StorageService;
import com.factly.dega.service.impl.CloudStorageServiceImpl;
import com.factly.dega.service.impl.FileStorageServiceImpl;
import com.factly.dega.utils.FileNameUtils;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.CommonUtil;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.factly.dega.service.dto.MediaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import java.util.stream.Collectors;

/**
 * REST controller for managing Media.
 */
@RestController
@RequestMapping("/api")
public class MediaResource {

    private final Logger log = LoggerFactory.getLogger(MediaResource.class);

    private static final String ENTITY_NAME = "coreMedia";

    private final MediaService mediaService;
    private final String bucketName;
    private final FileNameUtils fileNameUtils;
    private final String hostName;
    private final String mediaStorageRootDir;
    private final String storageType;
    private final String storageURL;
    private final boolean imageProxyEnabled;
    private final String imageProxyHost;


    private StorageService storageService;



    public MediaResource(MediaService mediaService, @Value("${google.cloud.storage.bucketname}") String bucketName,
                         FileNameUtils fileNameUtils, @Value("${dega.media.hostname}") String hostName,
                         @Value("${dega.media.upload-dir}") String mediaStorageRootDir,
                         @Value("${dega.media.storage}") String storageType,
                         @Value("${google.cloud.storage.hostname}") String storageURL,
                         @Value("${imageproxy.enabled}") boolean imageProxyEnabled,
                         @Value("${imageproxy.hostname}") String imageProxyHost) {
        this.mediaService = mediaService;
        this.bucketName = bucketName;
        this.fileNameUtils = fileNameUtils;
        this.hostName = hostName;
        this.mediaStorageRootDir = mediaStorageRootDir;
        this.storageType = storageType;
        this.storageURL = storageURL;
        this.imageProxyEnabled = imageProxyEnabled;
        this.imageProxyHost = imageProxyHost;
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
        mediaDTO.setClientId(null);
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
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
                                                HttpServletRequest request) throws URISyntaxException, IOException {
        MediaDTO mediaDTO = new MediaDTO();
        log.debug("REST request to save Media : {}", mediaDTO);
        String client = (String)request.getSession().getAttribute(Constants.CLIENT_ID);

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        int month = localDate.getMonthValue();
        String originalFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));
        MediaUrls mediaUrls = getStorageService(storageType).storeFile(file, client, year, month);
        mediaDTO.setName(mediaUrls.getUrl());

        // set the default slug by removing all special chars except letters and numbers
        mediaDTO.setSlug(getSlug(client, originalFileName));
        mediaDTO.setTitle(originalFileName);
        mediaDTO.setAltText(originalFileName);

        String fileSep = System.getProperty("file.separator");
        String filePath = "dega-content" + fileSep + client + fileSep + year + fileSep + month;
        if (storageType.equals("gcs")) {
            mediaDTO.setUrl(mediaUrls.getUrl());
            mediaDTO.setRelativeURL(mediaUrls.getRelativeURL());
            mediaDTO.setSourceURL(mediaUrls.getSourceURL());
        } else {
            mediaDTO.setUrl(hostName + fileSep + filePath + fileSep + mediaUrls.getUrl());
        }
        //mediaDTO.setSlug(fileDownloadUri);

        Object user = request.getSession().getAttribute(Constants.USER_ID);
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
            mediaDTO.setClientId(client);
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
    public ResponseEntity<MediaDTO> updateMedia(@Valid @RequestBody MediaDTO mediaDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Media : {}", mediaDTO);
        if (mediaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        mediaDTO.setClientId(null);
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
        if (obj != null) {
            mediaDTO.setClientId((String) obj);
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
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
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
    public ResponseEntity<List<MediaDTO>> searchMedia(@RequestParam String query, Pageable pageable, HttpServletRequest request) {
        log.debug("REST request to search for a page of Media for query {}", query);
        String clientId = (String) request.getSession().getAttribute(Constants.CLIENT_ID);
        Page<MediaDTO> page = mediaService.search(query, pageable);
        List<MediaDTO> mediaDTOList = page.getContent().stream().filter(mediaDTO -> mediaDTO.getClientId().equals(clientId)).collect(Collectors.toList());
        Page<MediaDTO> mediaDTOPage = new PageImpl<>(mediaDTOList, pageable, mediaDTOList.size());
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, mediaDTOPage, "/api/_search/media");
        return new ResponseEntity<>(mediaDTOPage.getContent(), headers, HttpStatus.OK);
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
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
        String clientId = null;
        if (obj != null) {
            clientId = (String) obj;
        }
        log.debug("REST request to get media by clienId : {} and slug : {}", clientId, slug);
        Optional<MediaDTO> mediaDTO = mediaService.findByClientIdAndSlug(clientId, slug);
        return mediaDTO;
    }

    public String getSlug(String clientId, String fileName){
        if(fileName != null){
            int slugExtention = 0;
            String tempSlug = CommonUtil.removeSpecialCharsFromString(fileName);
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

    private StorageService getStorageService(String storageType) {
        if (storageType.equals("gcs")) {
            storageService = new CloudStorageServiceImpl(bucketName, fileNameUtils, storageURL, imageProxyEnabled, imageProxyHost);
        } else {
            storageService = new FileStorageServiceImpl(mediaStorageRootDir);
        }
        return storageService;
    }

}
