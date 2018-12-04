package com.factly.dega.service.impl;

import com.factly.dega.service.FormatService;
import com.factly.dega.domain.Format;
import com.factly.dega.repository.FormatRepository;
import com.factly.dega.repository.search.FormatSearchRepository;
import com.factly.dega.service.dto.FormatDTO;
import com.factly.dega.service.mapper.FormatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Format.
 */
@Service
public class FormatServiceImpl implements FormatService {

    private final Logger log = LoggerFactory.getLogger(FormatServiceImpl.class);

    private final FormatRepository formatRepository;

    private final FormatMapper formatMapper;

    private final FormatSearchRepository formatSearchRepository;

    public FormatServiceImpl(FormatRepository formatRepository, FormatMapper formatMapper, FormatSearchRepository formatSearchRepository) {
        this.formatRepository = formatRepository;
        this.formatMapper = formatMapper;
        this.formatSearchRepository = formatSearchRepository;
    }

    /**
     * Save a format.
     *
     * @param formatDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FormatDTO save(FormatDTO formatDTO) {
        log.debug("Request to save Format : {}", formatDTO);

        Format format = formatMapper.toEntity(formatDTO);
        format = formatRepository.save(format);
        FormatDTO result = formatMapper.toDto(format);
        formatSearchRepository.save(format);
        return result;
    }

    /**
     * Get all the formats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<FormatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Formats");
        return formatRepository.findAll(pageable)
            .map(formatMapper::toDto);
    }


    /**
     * Get one format by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<FormatDTO> findOne(String id) {
        log.debug("Request to get Format : {}", id);
        return formatRepository.findById(id)
            .map(formatMapper::toDto);
    }

    /**
     * Delete the format by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Format : {}", id);
        formatRepository.deleteById(id);
        formatSearchRepository.deleteById(id);
    }

    /**
     * Search for the format corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<FormatDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Formats for query {}", query);
        return formatSearchRepository.search(queryStringQuery(query), pageable)
            .map(formatMapper::toDto);
    }

    /**
     * Get the format by clientId and slug.
     *
     * @param clientId the clientId of the FormatDTO
     * @param slug the slug of the FormatDTO
     * @return Optional<FormatDTO> post by clientId and slug
     */
    @Override
    public Optional<FormatDTO> findByClientIdAndSlug(String clientId, String slug) {
        log.debug("Request to get post  by clienId : {} and slug : {}", clientId, slug);
        return formatRepository.findByClientIdAndSlug(clientId, slug)
            .map(formatMapper::toDto);
    }
}
