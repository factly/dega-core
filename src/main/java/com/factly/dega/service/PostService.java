package com.factly.dega.service;

import com.factly.dega.service.dto.PostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Post.
 */
public interface PostService {

    /**
     * Save a post.
     *
     * @param postDTO the entity to save
     * @return the persisted entity
     */
    PostDTO save(PostDTO postDTO);

    /**
     * Get all the posts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PostDTO> findAll(Pageable pageable);

    /**
     * Get all the Post with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<PostDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" post.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PostDTO> findOne(String id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the post corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PostDTO> search(String query, Pageable pageable);
}
