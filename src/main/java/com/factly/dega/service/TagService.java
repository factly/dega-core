package com.factly.dega.service;

import com.factly.dega.service.dto.TagDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Tag.
 */
public interface TagService {

    /**
     * Save a tag.
     *
     * @param tagDTO the entity to save
     * @return the persisted entity
     */
    TagDTO save(TagDTO tagDTO);

    /**
     * Get all the tags.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TagDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tag.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TagDTO> findOne(String id);

    /**
     * Delete the "id" tag.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the tag corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TagDTO> search(String query, Pageable pageable);

    /**
     * Get the tag by clientId and slug.
     *
     * @param clientId the clientId of the TagDTO
     * @param slug the slug of the TagDTO
     * @return Optional<TagDTO> post by clientId and slug
     */
    Optional<TagDTO> findByClientIdAndSlug(String clientId, String slug);

    /**
     * Get all tags by client id.
     *
     * @param clientId the client id
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TagDTO> findByClientId(String clientId, Pageable pageable);
}
