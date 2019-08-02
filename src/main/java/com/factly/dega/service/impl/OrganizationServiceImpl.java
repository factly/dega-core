package com.factly.dega.service.impl;

import com.factly.dega.service.OrganizationService;
import com.factly.dega.domain.Organization;
import com.factly.dega.repository.OrganizationRepository;
import com.factly.dega.repository.search.OrganizationSearchRepository;
import com.factly.dega.service.RoleMappingService;
import com.factly.dega.service.RoleService;
import com.factly.dega.service.dto.OrganizationDTO;
import com.factly.dega.service.dto.RoleDTO;
import com.factly.dega.service.dto.RoleMappingDTO;
import com.factly.dega.service.mapper.OrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Organization.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    private final OrganizationSearchRepository organizationSearchRepository;

    private final RoleService roleService;

    private final RoleMappingService roleMappingService;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper,
                                   OrganizationSearchRepository organizationSearchRepository,
                                   RoleService roleService, RoleMappingService roleMappingService) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.organizationSearchRepository = organizationSearchRepository;
        this.roleService = roleService;
        this.roleMappingService = roleMappingService;
    }

    /**
     * Save a organization.
     *
     * @param organizationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        log.debug("Request to save Organization : {}", organizationDTO);

        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        OrganizationDTO result = organizationMapper.toDto(organization);
        organizationSearchRepository.save(organization);
        createRoleMappings(result, roleService, roleMappingService);
        return result;
    }

    private void createRoleMappings(OrganizationDTO organizationDTO, RoleService roleService, RoleMappingService roleMappingService) {
        List<RoleDTO> roles = roleService.findAll(PageRequest.of(0, 10)).getContent();
        List<RoleMappingDTO> roleMappings = roles.stream().filter(role -> !role.getName().equalsIgnoreCase("Super Admin")).map(role -> {
            RoleMappingDTO roleMapping = new RoleMappingDTO();
            roleMapping.setName(organizationDTO.getName() + " - " + role.getName());
            roleMapping.setOrganizationId(organizationDTO.getId());
            roleMapping.setOrganizationName(organizationDTO.getName());
            roleMapping.setRoleId(role.getId());
            roleMapping.setRoleName(role.getName());
            roleMapping.setKeycloakId(role.getKeycloakId());
            roleMapping.setKeycloakName(role.getKeycloakName());
            return roleMapping;
        }).collect(Collectors.toList());
        roleMappingService.saveAll(roleMappings);
    }

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<OrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll(pageable)
            .map(organizationMapper::toDto);
    }


    /**
     * Get one organization by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<OrganizationDTO> findOne(String id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findById(id)
            .map(organizationMapper::toDto);
    }

    /**
     * Delete the organization by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.deleteById(id);
        organizationSearchRepository.deleteById(id);
    }

    /**
     * Search for the organization corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<OrganizationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Organizations for query {}", query);
        return organizationSearchRepository.search(queryStringQuery(query), pageable)
            .map(organizationMapper::toDto);
    }

    /**
     * Get the organization by clientId and slug.
     *
     * @param slug the slug of the OrganizationDTO
     * @return Optional<OrganizationDTO> organization by clientId and slug
     */
    @Override
    public Optional<OrganizationDTO> findBySlug(String slug) {
        log.debug("Request to get Organization by slug : {}", slug);
        return organizationRepository.findBySlug(slug)
            .map(organizationMapper::toDto);
    }

}
