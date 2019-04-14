package com.factly.dega.service;

import java.util.Optional;
import com.factly.dega.service.dto.StatusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Status.
 */
public interface StatusService {

    /**
     * Save a status.
     *
     * @param statusDTO the entity to save
     * @return the persisted entity
     */
    StatusDTO save(StatusDTO statusDTO);

    /**
     * Get all the statuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" status.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StatusDTO> findOne(String id);

    /**
     * Get the "name" status.
     *
     * @param name the name of the entity
     * @return the entity
     */
    Optional<StatusDTO> findOneByName(String name);

    /**
     * Delete the "id" status.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the status corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StatusDTO> search(String query, Pageable pageable);

    /**
     * Get the status by clientId and slug.
     *
     * @param clientId the clientId of the StatusDTO
     * @param slug the slug of the StatusDTO
     * @return Optional<StatusDTO> post by clientId and slug
     */
    Optional<StatusDTO> findByClientIdAndSlug(String clientId, String slug);
}
