package com.factly.dega.service;

import com.factly.dega.service.dto.CategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Category.
 */
public interface CategoryService {

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    CategoryDTO save(CategoryDTO categoryDTO);

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" category.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CategoryDTO> findOne(String id);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the category corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CategoryDTO> search(String query, Pageable pageable);

    /**
     * Get the category by clientId and slug.
     *
     * @param clientId the clientId of the CategoryDTO
     * @param slug the slug of the CategoryDTO
     * @return Optional<CategoryDTO> post by clientId and slug
     */
    Optional<CategoryDTO> findByClientIdAndSlug(String clientId, String slug);

    /**
     * Get all the posts by client id.
     *
     * @param clientId the client id
     * @param pageable the pagination information
     * @return the list of entities
    */
    Page<CategoryDTO> findByClientId(String clientId, Pageable pageable);
}
