package com.factly.dega.service.impl;

import com.factly.dega.service.RoleMappingService;
import com.factly.dega.domain.RoleMapping;
import com.factly.dega.repository.RoleMappingRepository;
import com.factly.dega.repository.search.RoleMappingSearchRepository;
import com.factly.dega.service.dto.RoleMappingDTO;
import com.factly.dega.service.mapper.RoleMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RoleMapping.
 */
@Service
public class RoleMappingServiceImpl implements RoleMappingService {

    private final Logger log = LoggerFactory.getLogger(RoleMappingServiceImpl.class);

    private final RoleMappingRepository roleMappingRepository;

    private final RoleMappingMapper roleMappingMapper;

    private final RoleMappingSearchRepository roleMappingSearchRepository;

    public RoleMappingServiceImpl(RoleMappingRepository roleMappingRepository, RoleMappingMapper roleMappingMapper, RoleMappingSearchRepository roleMappingSearchRepository) {
        this.roleMappingRepository = roleMappingRepository;
        this.roleMappingMapper = roleMappingMapper;
        this.roleMappingSearchRepository = roleMappingSearchRepository;
    }

    /**
     * Save a roleMapping.
     *
     * @param roleMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoleMappingDTO save(RoleMappingDTO roleMappingDTO) {
        log.debug("Request to save RoleMapping : {}", roleMappingDTO);

        RoleMapping roleMapping = roleMappingMapper.toEntity(roleMappingDTO);
        roleMapping = roleMappingRepository.save(roleMapping);
        RoleMappingDTO result = roleMappingMapper.toDto(roleMapping);
        roleMappingSearchRepository.save(roleMapping);
        return result;
    }

    /**
     * Get all the roleMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<RoleMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoleMappings");
        return roleMappingRepository.findAll(pageable)
            .map(roleMappingMapper::toDto);
    }


    /**
     * Get one roleMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<RoleMappingDTO> findOne(String id) {
        log.debug("Request to get RoleMapping : {}", id);
        return roleMappingRepository.findById(id)
            .map(roleMappingMapper::toDto);
    }

    /**
     * Delete the roleMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete RoleMapping : {}", id);
        roleMappingRepository.deleteById(id);
        roleMappingSearchRepository.deleteById(id);
    }

    /**
     * Search for the roleMapping corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<RoleMappingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RoleMappings for query {}", query);
        return roleMappingSearchRepository.search(queryStringQuery(query), pageable)
            .map(roleMappingMapper::toDto);
    }
}
