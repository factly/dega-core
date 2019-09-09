package com.factly.dega.service.impl;

import com.factly.dega.service.MediaService;
import com.factly.dega.service.PostService;
import com.factly.dega.domain.Post;
import com.factly.dega.repository.PostRepository;
import com.factly.dega.repository.search.PostSearchRepository;
import com.factly.dega.service.dto.MediaDTO;
import com.factly.dega.service.dto.PostDTO;
import com.factly.dega.service.mapper.PostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Post.
 */
@Service
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final PostSearchRepository postSearchRepository;

    private final MediaService mediaService;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, PostSearchRepository postSearchRepository, MediaService mediaService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.postSearchRepository = postSearchRepository;
        this.mediaService = mediaService;
    }

    /**
     * Save a post.
     *
     * @param postDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);

        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        PostDTO result = postMapper.toDto(post);
        if(result.getMedia() != null && !result.getMedia().getId().isEmpty()) {
            Optional<MediaDTO> mediaDTO = mediaService.findOne(result.getMedia().getId());
            if (mediaDTO.isPresent()) {
                result.setMedia(mediaDTO.get());
            }
        }
        postSearchRepository.save(post);
        return result;
    }

    /**
     * Get all the posts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<PostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable)
            .map(postMapper::toDto);
    }

    /**
     * Get all the Post with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PostDTO> findAllWithEagerRelationships(Pageable pageable) {
        return postRepository.findAllWithEagerRelationships(pageable).map(postMapper::toDto);
    }


    /**
     * Get one post by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<PostDTO> findOne(String id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findOneWithEagerRelationships(id)
            .map(postMapper::toDto);
    }

    /**
     * Delete the post by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
        postSearchRepository.deleteById(id);
    }

    /**
     * Search for the post corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<PostDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Posts for query {}", query);
        return postSearchRepository.search(queryStringQuery(query), pageable)
            .map(postMapper::toDto);
    }

    /**
     * Get all posts by clientId.
     *
     * @param clientId the client id of the entity
     * @return the entity
     */
    @Override
    public Page<PostDTO> findByClientId(String clientId, Pageable pageable) {
        log.debug("Request to get Post : {}", clientId);
        return postRepository.findByClientId(clientId, pageable).map(postMapper::toDto);
    }

    /**
     * Get the post by clientId and slug.
     *
     * @param clientId the clientId of the postDTO
     * @param slug the slug of the PostDTO
     * @return Optional<PostDTO> post by clientId and slug
     */
    @Override
    public Optional<PostDTO> findByClientIdAndSlug(String clientId, String slug) {
        log.debug("Request to get post  by clienId : {} and slug : {}", clientId, slug);
        return postRepository.findByClientIdAndSlug(clientId, slug)
            .map(postMapper::toDto);
    }
}
