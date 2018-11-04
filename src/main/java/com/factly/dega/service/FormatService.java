package com.factly.dega.service;

import com.factly.dega.service.dto.FormatDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Format.
 */
public interface FormatService {

    /**
     * Save a format.
     *
     * @param formatDTO the entity to save
     * @return the persisted entity
     */
    FormatDTO save(FormatDTO formatDTO);

    /**
     * Get all the formats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FormatDTO> findAll(Pageable pageable);


    /**
     * Get the "id" format.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FormatDTO> findOne(String id);

    /**
     * Delete the "id" format.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the format corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FormatDTO> search(String query, Pageable pageable);
}
