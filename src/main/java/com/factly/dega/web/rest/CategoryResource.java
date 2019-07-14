package com.factly.dega.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.dega.config.Constants;
import com.factly.dega.service.CategoryService;
import com.factly.dega.web.rest.errors.BadRequestAlertException;
import com.factly.dega.web.rest.util.CommonUtil;
import com.factly.dega.web.rest.util.HeaderUtil;
import com.factly.dega.web.rest.util.PaginationUtil;
import com.factly.dega.service.dto.CategoryDTO;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "coreCategory";

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * POST  /categories : Create a new category.
     *
     * @param categoryDTO the categoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoryDTO, or with status 400 (Bad Request) if the category has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categories")
    @Timed
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Category : {}", categoryDTO);
        if (categoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new category cannot already have an ID", ENTITY_NAME, "idexists");
        }
        categoryDTO.setClientId(null);
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
        if (obj != null) {
            categoryDTO.setClientId((String) obj);
        }
        categoryDTO.setSlug(getSlug((String) obj, CommonUtil.removeSpecialCharsFromString(categoryDTO.getName())));
        categoryDTO.setCreatedDate(ZonedDateTime.now());
        categoryDTO.setLastUpdatedDate(ZonedDateTime.now());
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categories : Updates an existing category.
     *
     * @param categoryDTO the categoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoryDTO,
     * or with status 400 (Bad Request) if the categoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the categoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categories")
    @Timed
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Category : {}", categoryDTO);
        if (categoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<CategoryDTO> savedCategoryData = categoryService.findOne(categoryDTO.getId());
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
        if (savedCategoryData.isPresent()) {
            if (savedCategoryData.get().getClientId() != obj){
                throw new BadRequestAlertException("You are not allowed to update this client entries", ENTITY_NAME, "invalidclient");
            }
            categoryDTO.setClientId(savedCategoryData.get().getClientId());
        } else {
            throw new BadRequestAlertException("Category does not exist", ENTITY_NAME, "invalidcategory");
        }

        // If a slug is updated from client.
        if (!categoryDTO.getSlug().equals(savedCategoryData.get().getSlug())){
            // Slug needs to be verified in db, if a slug exists with the same text then add auto extension of digit.
            categoryDTO.setSlug(getSlug((String) obj, CommonUtil.removeSpecialCharsFromString(categoryDTO.getSlug())));
        }

        categoryDTO.setLastUpdatedDate(ZonedDateTime.now());
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categories : get all the categories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of categories in body
     */
    @GetMapping("/categories")
    @Timed
    public ResponseEntity<List<CategoryDTO>> getAllCategories(Pageable pageable, HttpServletRequest request) {
        log.debug("REST request to get a page of Categories");
        Page<CategoryDTO> page = new PageImpl(new ArrayList<>());
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
        if (obj != null) {
            String clientId = (String) obj;
            page = categoryService.findByClientId(clientId, pageable);

        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /categories/:id : get the "id" category.
     *
     * @param id the id of the categoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categories/{id}")
    @Timed
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable String id) {
        log.debug("REST request to get Category : {}", id);
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryDTO);
    }

    /**
     * DELETE  /categories/:id : delete the "id" category.
     *
     * @param id the id of the categoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        log.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/categories?query=:query : search for the category corresponding
     * to the query.
     *
     * @param query the query of the category search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/categories")
    @Timed
    public ResponseEntity<List<CategoryDTO>> searchCategories(@RequestParam String query, Pageable pageable, HttpServletRequest request) {
        log.debug("REST request to search for a page of Categories for query {}", query);
        String clientId = (String) request.getSession().getAttribute(Constants.CLIENT_ID);
        Page<CategoryDTO> page = categoryService.search(query, pageable);
        List<CategoryDTO> categoriesByClientId = page.getContent().stream().filter(categories -> categories.getClientId().equals(clientId)).collect(Collectors.toList());
        Page<CategoryDTO> categoryDTOPage = new PageImpl<>(categoriesByClientId, pageable, categoriesByClientId.size());
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, categoryDTOPage, "/api/_search/categories");
        return new ResponseEntity<>(categoryDTOPage.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /categorybyslug/:slug : get the category.
     *
     * @param slug the slug of the CategoryDTO
     * @param request
     * @return Optional<CategoryDTO> category by clientId and slug
     */
    @GetMapping("/categorybyslug/{slug}")
    @Timed
    public Optional<CategoryDTO> getCategoryBySlug(@PathVariable String slug, HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(Constants.CLIENT_ID);
        String clientId = null;
        if (obj != null) {
            clientId = (String) obj;
        }
        log.debug("REST request to get Category by clienId : {} and slug : {}", clientId, slug);
        Optional<CategoryDTO> categoryDTO = categoryService.findByClientIdAndSlug(clientId, slug);
        return categoryDTO;
    }

    public String getSlug(String clientId, String name){
        if(clientId != null && name != null){
            int slugExtention = 0;
            return createSlug(clientId, name, name, slugExtention);
        }
        return null;
    }

    public String createSlug(String clientId, String slug, String tempSlug, int slugExtention){
        Optional<CategoryDTO> categoryDTO = categoryService.findByClientIdAndSlug(clientId, slug);
        if(categoryDTO.isPresent()){
            slugExtention += 1;
            slug = tempSlug + slugExtention;
            return createSlug(clientId, slug, tempSlug, slugExtention);
        }
        return slug;
    }

}
