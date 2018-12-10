package com.factly.dega.service;

import com.factly.dega.service.dto.OrganizationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Organization.
 */
public interface OrganizationService {

    /**
     * Save a organization.
     *
     * @param organizationDTO the entity to save
     * @return the persisted entity
     */
    OrganizationDTO save(OrganizationDTO organizationDTO);

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrganizationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" organization.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrganizationDTO> findOne(String id);

    /**
     * Delete the "id" organization.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the organization corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrganizationDTO> search(String query, Pageable pageable);

    /**
     * Get the organization by clientId and slug.
     *
     * @param clientId the clientId of the OrganizationDTO
     * @param slug the slug of the OrganizationDTO
     * @return Optional<OrganizationDTO> post by clientId and slug
     */
    Optional<OrganizationDTO> findByClientIdAndSlug(String clientId, String slug);

    /**
     * Get all the organizations by client id.
     *
     * @param clientId the client id
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrganizationDTO> findByClientId(String clientId, Pageable pageable);
}
