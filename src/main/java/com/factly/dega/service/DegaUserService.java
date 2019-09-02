package com.factly.dega.service;

import com.factly.dega.service.dto.DegaUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DegaUser.
 */
public interface DegaUserService {

    /**
     * Save a degaUser.
     *
     * @param degaUserDTO the entity to save
     * @return the persisted entity
     */
    DegaUserDTO save(DegaUserDTO degaUserDTO);

    /**
     * Get all the degaUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DegaUserDTO> findAll(Pageable pageable);

    /**
     * Get all the DegaUser with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<DegaUserDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" degaUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DegaUserDTO> findOne(String id);

    /**
     * Delete the "id" degaUser.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the degaUser corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DegaUserDTO> search(String query, Pageable pageable);

    /**
     * Get the "emailId" degaUser.
     *
     * @param emailId the id of the entity
     * @return the entity
     */
    Optional<DegaUserDTO> findByEmailId(String emailId);

    /**
     * Get the degaUser by clientId and slug.
     *
     * @param slug the slug of the DegaUserDTO
     * @return Optional<DegaUserDTO> post by clientId and slug
     */
    Optional<DegaUserDTO> findBySlug(String slug);

    /**
     * Get the degaUser by keycloakId.
     *
     * @param keycloakId the id of the entity
     * @return the entity
     */
    Optional<DegaUserDTO> findByKeycloakId(String keycloakId);
}
