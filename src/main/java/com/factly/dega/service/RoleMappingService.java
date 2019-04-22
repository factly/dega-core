package com.factly.dega.service;

import com.factly.dega.service.dto.RoleMappingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RoleMapping.
 */
public interface RoleMappingService {

    /**
     * Save a roleMapping.
     *
     * @param roleMappingDTO the entity to save
     * @return the persisted entity
     */
    RoleMappingDTO save(RoleMappingDTO roleMappingDTO);

    /**
     * Get all the roleMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoleMappingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" roleMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoleMappingDTO> findOne(String id);

    /**
     * Delete the "id" roleMapping.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the roleMapping corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoleMappingDTO> search(String query, Pageable pageable);
}
