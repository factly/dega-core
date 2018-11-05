package com.factly.dega.service.impl;

import com.factly.dega.service.MediaService;
import com.factly.dega.domain.Media;
import com.factly.dega.repository.MediaRepository;
import com.factly.dega.repository.search.MediaSearchRepository;
import com.factly.dega.service.dto.MediaDTO;
import com.factly.dega.service.mapper.MediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Media.
 */
@Service
public class MediaServiceImpl implements MediaService {

    private final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper;

    private final MediaSearchRepository mediaSearchRepository;

    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper, MediaSearchRepository mediaSearchRepository) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
        this.mediaSearchRepository = mediaSearchRepository;
    }

    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MediaDTO save(MediaDTO mediaDTO) {
        log.debug("Request to save Media : {}", mediaDTO);

        Media media = mediaMapper.toEntity(mediaDTO);
        media = mediaRepository.save(media);
        MediaDTO result = mediaMapper.toDto(media);
        mediaSearchRepository.save(media);
        return result;
    }

    /**
     * Get all the media.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<MediaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Media");
        return mediaRepository.findAll(pageable)
            .map(mediaMapper::toDto);
    }


    /**
     * Get one media by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<MediaDTO> findOne(String id) {
        log.debug("Request to get Media : {}", id);
        return mediaRepository.findById(id)
            .map(mediaMapper::toDto);
    }

    /**
     * Delete the media by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Media : {}", id);
        mediaRepository.deleteById(id);
        mediaSearchRepository.deleteById(id);
    }

    /**
     * Search for the media corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<MediaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Media for query {}", query);
        return mediaSearchRepository.search(queryStringQuery(query), pageable)
            .map(mediaMapper::toDto);
    }
}
