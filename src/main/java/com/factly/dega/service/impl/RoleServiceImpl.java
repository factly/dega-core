package com.factly.dega.service.impl;

import com.factly.dega.service.RoleService;
import com.factly.dega.domain.Role;
import com.factly.dega.repository.RoleRepository;
import com.factly.dega.repository.search.RoleSearchRepository;
import com.factly.dega.service.dto.RoleDTO;
import com.factly.dega.service.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Role.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final RoleSearchRepository roleSearchRepository;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, RoleSearchRepository roleSearchRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.roleSearchRepository = roleSearchRepository;
    }

    /**
     * Save a role.
     *
     * @param roleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        log.debug("Request to save Role : {}", roleDTO);

        Role role = roleMapper.toEntity(roleDTO);
        role = roleRepository.save(role);
        RoleDTO result = roleMapper.toDto(role);
        roleSearchRepository.save(role);
        return result;
    }

    /**
     * Get all the roles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<RoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        return roleRepository.findAll(pageable)
            .map(roleMapper::toDto);
    }


    /**
     * Get one role by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<RoleDTO> findOne(String id) {
        log.debug("Request to get Role : {}", id);
        return roleRepository.findById(id)
            .map(roleMapper::toDto);
    }

    /**
     * Delete the role by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.deleteById(id);
        roleSearchRepository.deleteById(id);
    }

    /**
     * Search for the role corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<RoleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Roles for query {}", query);
        return roleSearchRepository.search(queryStringQuery(query), pageable)
            .map(roleMapper::toDto);
    }

    /**
     * Get the role by clientId and slug.
     *
     * @param clientId the clientId of the RoleDTO
     * @param slug the slug of the RoleDTO
     * @return Optional<RoleDTO> roledto by clientId and slug
     */
    @Override
    public Optional<RoleDTO> findByClientIdAndSlug(String clientId, String slug) {
        log.debug("Request to get post  by clienId : {} and slug : {}", clientId, slug);
        return roleRepository.findByClientIdAndSlug(clientId, slug)
            .map(roleMapper::toDto);
    }
}
