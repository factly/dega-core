package com.factly.dega.service.impl;

import com.factly.dega.service.StatusService;
import com.factly.dega.domain.Status;
import com.factly.dega.repository.StatusRepository;
import com.factly.dega.repository.search.StatusSearchRepository;
import com.factly.dega.service.dto.StatusDTO;
import com.factly.dega.service.mapper.StatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Status.
 */
@Service
public class StatusServiceImpl implements StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    private final StatusSearchRepository statusSearchRepository;

    public StatusServiceImpl(StatusRepository statusRepository, StatusMapper statusMapper, StatusSearchRepository statusSearchRepository) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
        this.statusSearchRepository = statusSearchRepository;
    }

    /**
     * Save a status.
     *
     * @param statusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StatusDTO save(StatusDTO statusDTO) {
        log.debug("Request to save Status : {}", statusDTO);

        Status status = statusMapper.toEntity(statusDTO);
        status = statusRepository.save(status);
        StatusDTO result = statusMapper.toDto(status);
        statusSearchRepository.save(status);
        return result;
    }

    /**
     * Get all the statuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<StatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll(pageable)
            .map(statusMapper::toDto);
    }


    /**
     * Get one status by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<StatusDTO> findOne(String id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findById(id)
            .map(statusMapper::toDto);
    }

    /**
     * Get one status by name.
     *
     * @param name the name of the entity
     * @return the entity
     */
    @Override
    public Optional<StatusDTO> findOneByName(String name) {
        log.debug("Request to get Status : {}", name);
        return statusRepository.findByName(name)
            .map(statusMapper::toDto);
    }

    /**
     * Delete the status by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
        statusSearchRepository.deleteById(id);
    }

    /**
     * Search for the status corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<StatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Statuses for query {}", query);
        return statusSearchRepository.search(queryStringQuery(query), pageable)
            .map(statusMapper::toDto);
    }
}
