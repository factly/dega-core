package com.factly.dega.service.impl;

import com.factly.dega.service.DegaUserService;
import com.factly.dega.service.MediaService;
import com.factly.dega.service.OrganizationService;
import com.factly.dega.domain.Organization;
import com.factly.dega.repository.OrganizationRepository;
import com.factly.dega.repository.search.OrganizationSearchRepository;
import com.factly.dega.service.RoleMappingService;
import com.factly.dega.service.RoleService;
import com.factly.dega.service.dto.DegaUserDTO;
import com.factly.dega.service.dto.OrganizationDTO;
import com.factly.dega.service.dto.MediaDTO;
import com.factly.dega.service.dto.RoleDTO;
import com.factly.dega.service.dto.RoleMappingDTO;
import com.factly.dega.service.mapper.OrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    private final DegaUserService degaUserService;

    private final MediaService mediaService;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper,
                                   OrganizationSearchRepository organizationSearchRepository,
                                   RoleService roleService, RoleMappingService roleMappingService,
                                   DegaUserService degaUserService,
                                   MediaService mediaService) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.organizationSearchRepository = organizationSearchRepository;
        this.roleService = roleService;
        this.roleMappingService = roleMappingService;
        this.degaUserService = degaUserService;
        this.mediaService = mediaService;
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
        if(result.getMediaLogo() != null && !result.getMediaLogo().getId().isEmpty()){
            Optional<MediaDTO> mediaLogoDTO = mediaService.findOne(result.getMediaLogo().getId());
            if(mediaLogoDTO.isPresent()) {
                result.setMediaLogo(mediaLogoDTO.get());
            }
        }

        if(result.getMediaMobileLogo() != null && !result.getMediaMobileLogo().getId().isEmpty()){
            Optional<MediaDTO> mediaMobileLogoDTO = mediaService.findOne(result.getMediaMobileLogo().getId());
            if(mediaMobileLogoDTO.isPresent()) {
                result.setMediaMobileLogo(mediaMobileLogoDTO.get());
            }
        }

        if(result.getMediaFavicon() != null && !result.getMediaFavicon().getId().isEmpty()){
            Optional<MediaDTO> mediaFaviconDTO = mediaService.findOne(result.getMediaFavicon().getId());
            if(mediaFaviconDTO.isPresent()) {
                result.setMediaFavicon(mediaFaviconDTO.get());
            }
        }

        if(result.getMediaMobileIcon() != null && !result.getMediaMobileIcon().getId().isEmpty()){
            Optional<MediaDTO> mediaMobileIconDTO = mediaService.findOne(result.getMediaMobileIcon().getId());
            if(mediaMobileIconDTO.isPresent()) {
                result.setMediaMobileIcon(mediaMobileIconDTO.get());
            }
        }

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

    /**
     * Get the organizations by keycloakId and pageable.
     *
     * @param keycloakUserId the keycloakId of the User
     * @param pageable the pagination information
     *
     * @return List<OrganizationDTO> List of Organizations by keycloakId and pageable
     */
    public List<OrganizationDTO> getOrganizations(String keycloakUserId, Pageable pageable){

        Optional<DegaUserDTO> degaUserDTO = degaUserService.findByKeycloakId(keycloakUserId);

        if (degaUserDTO.get() == null) {
            return null;
        }
        DegaUserDTO degaUser = degaUserDTO.get();
        Boolean isSuperAdmin = degaUser.isIsSuperAdmin();
        if (isSuperAdmin != null && isSuperAdmin == true) {
            // return all orgs
            Page<OrganizationDTO> page = findAll(pageable);
            return page.getContent();
        } else {
            Set<RoleMappingDTO> roleMappings = degaUser.getRoleMappings();
            Set<OrganizationDTO> orgDTOs = new HashSet<>();
            roleMappings.stream().forEach(rm -> {
                OrganizationDTO organizationDTO = new OrganizationDTO();
                organizationDTO.setName(rm.getOrganizationName());
                organizationDTO.setId(rm.getOrganizationId());
                orgDTOs.add(organizationDTO);
            });
            List<OrganizationDTO> orgsList = orgDTOs.stream().collect(Collectors.toList());

            return orgsList;
        }
    }

}
