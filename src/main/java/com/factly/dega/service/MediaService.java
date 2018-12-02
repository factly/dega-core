package com.factly.dega.service;

import com.factly.dega.service.dto.MediaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Media.
 */
public interface MediaService {

    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save
     * @return the persisted entity
     */
    MediaDTO save(MediaDTO mediaDTO);

    /**
     * Get all the media.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MediaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" media.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MediaDTO> findOne(String id);

    /**
     * Delete the "id" media.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the media corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MediaDTO> search(String query, Pageable pageable);

    /**
     * Get the media by clientId and slug.
     *
     * @param clientId the clientId of the MediaDTO
     * @param slug the slug of the MediaDTO
     * @return Optional<MediaDTO> post by clientId and slug
     */
    Optional<MediaDTO> findByClientIdAndSlug(String clientId, String slug);
}
