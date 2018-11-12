package com.factly.dega.service.impl;

import com.factly.dega.service.DegaUserService;
import com.factly.dega.domain.DegaUser;
import com.factly.dega.repository.DegaUserRepository;
import com.factly.dega.repository.search.DegaUserSearchRepository;
import com.factly.dega.service.dto.DegaUserDTO;
import com.factly.dega.service.mapper.DegaUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DegaUser.
 */
@Service
public class DegaUserServiceImpl implements DegaUserService {

    private final Logger log = LoggerFactory.getLogger(DegaUserServiceImpl.class);

    private final DegaUserRepository degaUserRepository;

    private final DegaUserMapper degaUserMapper;

    private final DegaUserSearchRepository degaUserSearchRepository;

    public DegaUserServiceImpl(DegaUserRepository degaUserRepository, DegaUserMapper degaUserMapper, DegaUserSearchRepository degaUserSearchRepository) {
        this.degaUserRepository = degaUserRepository;
        this.degaUserMapper = degaUserMapper;
        this.degaUserSearchRepository = degaUserSearchRepository;
    }

    /**
     * Save a degaUser.
     *
     * @param degaUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DegaUserDTO save(DegaUserDTO degaUserDTO) {
        log.debug("Request to save DegaUser : {}", degaUserDTO);

        DegaUser degaUser = degaUserMapper.toEntity(degaUserDTO);
        degaUser = degaUserRepository.save(degaUser);
        DegaUserDTO result = degaUserMapper.toDto(degaUser);
        degaUserSearchRepository.save(degaUser);
        return result;
    }

    /**
     * Get all the degaUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DegaUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DegaUsers");
        return degaUserRepository.findAll(pageable)
            .map(degaUserMapper::toDto);
    }

    /**
     * Get all the DegaUser with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<DegaUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return degaUserRepository.findAllWithEagerRelationships(pageable).map(degaUserMapper::toDto);
    }


    /**
     * Get one degaUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<DegaUserDTO> findOne(String id) {
        log.debug("Request to get DegaUser : {}", id);
        return degaUserRepository.findOneWithEagerRelationships(id)
            .map(degaUserMapper::toDto);
    }

    /**
     * Delete the degaUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete DegaUser : {}", id);
        degaUserRepository.deleteById(id);
        degaUserSearchRepository.deleteById(id);
    }

    /**
     * Search for the degaUser corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DegaUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DegaUsers for query {}", query);
        return degaUserSearchRepository.search(queryStringQuery(query), pageable)
            .map(degaUserMapper::toDto);
    }

    /**
     * Get one degaUser by emailId.
     *
     * @param emailId the id of the entity
     * @return the entity
     */
    @Override
    public Optional<DegaUserDTO> findByEmailId(String emailId) {
        log.debug("Request to get DegaUser : {}", emailId);
        return degaUserRepository.findByEmailId(emailId)
            .map(degaUserMapper::toDto);
    }
}
