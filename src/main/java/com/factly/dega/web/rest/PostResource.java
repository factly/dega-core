package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.config.Constants;
import com.factly.dega.service.PostService;
import com.factly.dega.service.StatusService;
import com.factly.dega.service.dto.StatusDTO;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.factly.dega.service.dto.PostDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Post.
 */
@RestController
@RequestMapping("/api")
public class PostResource {

    private final Logger log = LoggerFactory.getLogger(PostResource.class);

    private static final String ENTITY_NAME = "corePost";

    private final PostService postService;

    private final StatusService statusService;

    public PostResource(PostService postService, StatusService statusService) {
        this.postService = postService;
        this.statusService = statusService;
    }

    /**
     * POST  /posts : Create a new post.
     *
     * @param postDTO the postDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new postDTO, or with status 400 (Bad Request) if the post has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/posts")
    @Timed
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Post : {}", postDTO);
        if (postDTO.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<StatusDTO> status = statusService.findOneByName("Draft");
        if (status.isPresent() && status.get() != null) {
            postDTO.setStatusId(status.get().getId());
        }
        Object obj = request.getAttribute(Constants.CLIENT_ID);
        if (obj != null) {
            postDTO.setClientId((String) obj);
        }
        postDTO.setCreatedDate(ZonedDateTime.now());
        postDTO.setLastUpdatedDate(ZonedDateTime.now());
        PostDTO result = postService.save(postDTO);
        return ResponseEntity.created(new URI("/api/posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /posts : Create a new post.
     *
     * @param postDTO the postDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new postDTO, or with status 400 (Bad Request) if the post has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publish")
    @Timed
    public ResponseEntity<PostDTO> publishPost(@Valid @RequestBody PostDTO postDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Post : {}", postDTO);
        if (postDTO.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<StatusDTO> status = statusService.findOneByName("Publish");
        if (status.get() != null) {
            postDTO.setStatusId(status.get().getId());
        }
        Object obj = request.getAttribute(Constants.CLIENT_ID);
        if (obj != null) {
            postDTO.setClientId((String) obj);
        }
        postDTO.setCreatedDate(ZonedDateTime.now());
        postDTO.setLastUpdatedDate(ZonedDateTime.now());
        postDTO.setPublishedDate(ZonedDateTime.now());
        PostDTO result = postService.save(postDTO);
        return ResponseEntity.created(new URI("/api/posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /posts : Updates an existing post.
     *
     * @param postDTO the postDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated postDTO,
     * or with status 400 (Bad Request) if the postDTO is not valid,
     * or with status 500 (Internal Server Error) if the postDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/posts")
    @Timed
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to update Post : {}", postDTO);
        if (postDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        postDTO.setLastUpdatedDate(ZonedDateTime.now());
        PostDTO result = postService.save(postDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, postDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /posts : get all the posts.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of posts in body
     */
    @GetMapping("/posts")
    @Timed
    public ResponseEntity<List<PostDTO>> getAllPosts(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload, HttpServletRequest request) {
        log.debug("REST request to get a page of Posts");
        Page<PostDTO> page = new PageImpl(new ArrayList<>());
        Object obj = request.getAttribute(Constants.CLIENT_ID);
        if (obj != null) {
            String clientId = (String) obj;
            page = postService.findByClientId(clientId, pageable);

        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/posts?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /posts/:id : get the "id" post.
     *
     * @param id the id of the postDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the postDTO, or with status 404 (Not Found)
     */
    @GetMapping("/posts/{id}")
    @Timed
    public ResponseEntity<PostDTO> getPost(@PathVariable String id) {
        log.debug("REST request to get Post : {}", id);
        Optional<PostDTO> postDTO = postService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postDTO);
    }

    /**
     * DELETE  /posts/:id : delete the "id" post.
     *
     * @param id the id of the postDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/posts/{id}")
    @Timed
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        log.debug("REST request to delete Post : {}", id);
        postService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/posts?query=:query : search for the post corresponding
     * to the query.
     *
     * @param query the query of the post search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/posts")
    @Timed
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Posts for query {}", query);
        Page<PostDTO> page = postService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/posts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /posts/:clientId/:slug : get the post.
     *
     * @param slug the slug of the PostDTO
     * @return Optional<PostDTO> post by clientId and slug
     */
    @GetMapping("/postbyslug/{slug}")
    @Timed
    public Optional<PostDTO> getPostBySlug(@PathVariable String slug, HttpServletRequest request) {
        Object obj = request.getAttribute(Constants.CLIENT_ID);
        String clientId = null;
        if (obj != null) {
            clientId = (String) obj;
        }
        log.debug("REST request to get post by clienId : {} and slug : {}", clientId, slug);
        Optional<PostDTO> postDTO = postService.findByClientIdAndSlug(clientId, slug);
        return postDTO;
    }

}
