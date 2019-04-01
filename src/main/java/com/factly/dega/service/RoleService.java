package com.factly.dega.service;

import com.factly.dega.service.dto.RoleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Role.
 */
public interface RoleService {

    /**
     * Save a role.
     *
     * @param roleDTO the entity to save
     * @return the persisted entity
     */
    RoleDTO save(RoleDTO roleDTO);

    /**
     * Get all the roles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" role.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoleDTO> findOne(String id);

    /**
     * Delete the "id" role.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the role corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoleDTO> search(String query, Pageable pageable);

    /**
     * Get the role by clientId and slug.
     *
     * @param clientId the clientId of the RoleDTO
     * @param slug the slug of the RoleDTO
     * @return Optional<RoleDTO> post by clientId and slug
     */
    Optional<RoleDTO> findByClientIdAndSlug(String clientId, String slug);
}
