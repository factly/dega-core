package com.factly.dega.web.rest;

import com.factly.dega.CoreApp;

import com.factly.dega.domain.Post;
import com.factly.dega.domain.Status;
import com.factly.dega.domain.Format;
import com.factly.dega.domain.DegaUser;
import com.factly.dega.repository.PostRepository;
import com.factly.dega.repository.search.PostSearchRepository;
import com.factly.dega.service.PostService;
import com.factly.dega.service.StatusService;
import com.factly.dega.service.dto.PostDTO;
import com.factly.dega.service.mapper.PostMapper;
import com.factly.dega.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.factly.dega.web.rest.TestUtil.sameInstant;
import static com.factly.dega.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PostResource REST controller.
 *
 * @see PostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class PostResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_EXCERPT = "AAAAAAAAAA";
    private static final String UPDATED_EXCERPT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PUBLISHED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PUBLISHED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PUBLISHED_DATE_GMT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PUBLISHED_DATE_GMT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE_GMT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE_GMT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_FEATURED = false;
    private static final Boolean UPDATED_FEATURED = true;

    private static final Boolean DEFAULT_STICKY = false;
    private static final Boolean UPDATED_STICKY = true;

    private static final String DEFAULT_UPDATES = "AAAAAAAAAA";
    private static final String UPDATED_UPDATES = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURED_MEDIA = "AAAAAAAAAA";
    private static final String UPDATED_FEATURED_MEDIA = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    @Autowired
    private PostRepository postRepository;

    @Mock
    private PostRepository postRepositoryMock;

    @Autowired
    private PostMapper postMapper;


    @Mock
    private PostService postServiceMock;

    @Autowired
    private PostService postService;

    @Mock
    private StatusService statusServiceMock;

    @Autowired
    private StatusService statusService;

    /**
     * This repository is mocked in the com.factly.dega.repository.search test package.
     *
     * @see com.factly.dega.repository.search.PostSearchRepositoryMockConfiguration
     */
    @Autowired
    private PostSearchRepository mockPostSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPostMockMvc;

    private Post post;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PostResource postResource = new PostResource(postService, statusService);
        this.restPostMockMvc = MockMvcBuilders.standaloneSetup(postResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity() {
        Post post = new Post()
            .title(DEFAULT_TITLE)
            .clientId(DEFAULT_CLIENT_ID)
            .content(DEFAULT_CONTENT)
            .excerpt(DEFAULT_EXCERPT)
            .publishedDate(DEFAULT_PUBLISHED_DATE)
            .publishedDateGMT(DEFAULT_PUBLISHED_DATE_GMT)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .lastUpdatedDateGMT(DEFAULT_LAST_UPDATED_DATE_GMT)
            .featured(DEFAULT_FEATURED)
            .sticky(DEFAULT_STICKY)
            .updates(DEFAULT_UPDATES)
            .slug(DEFAULT_SLUG)
            .password(DEFAULT_PASSWORD)
            .featuredMedia(DEFAULT_FEATURED_MEDIA)
            .subTitle(DEFAULT_SUB_TITLE);
        // Add required entity
        Status status = StatusResourceIntTest.createEntity();
        status.setId("fixed-id-for-tests");
        post.setStatus(status);
        // Add required entity
        Format format = FormatResourceIntTest.createEntity();
        format.setId("fixed-id-for-tests");
        post.setFormat(format);
        // Add required entity
        DegaUser degaUser = DegaUserResourceIntTest.createEntity();
        degaUser.setId("fixed-id-for-tests");
        post.getDegaUsers().add(degaUser);
        return post;
    }

    @Before
    public void initTest() {
        postRepository.deleteAll();
        post = createEntity();
    }

    @Test
    public void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);
        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPost.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testPost.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPost.getExcerpt()).isEqualTo(DEFAULT_EXCERPT);
        assertThat(testPost.getPublishedDate()).isEqualTo(DEFAULT_PUBLISHED_DATE);
        assertThat(testPost.getPublishedDateGMT()).isEqualTo(DEFAULT_PUBLISHED_DATE_GMT);
        assertThat(testPost.getLastUpdatedDate().toLocalDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE.toLocalDate());
        assertThat(testPost.getLastUpdatedDateGMT().toLocalDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE_GMT.toLocalDate());
        assertThat(testPost.isFeatured()).isEqualTo(DEFAULT_FEATURED);
        assertThat(testPost.isSticky()).isEqualTo(DEFAULT_STICKY);
        assertThat(testPost.getUpdates()).isEqualTo(DEFAULT_UPDATES);
        assertThat(testPost.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testPost.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testPost.getFeaturedMedia()).isEqualTo(DEFAULT_FEATURED_MEDIA);
        assertThat(testPost.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);

        // Validate the Post in Elasticsearch
        verify(mockPostSearchRepository, times(1)).save(testPost);
    }

    @Test
    public void createPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post with an existing ID
        post.setId("existing_id");
        PostDTO postDTO = postMapper.toDto(post);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);

        // Validate the Post in Elasticsearch
        verify(mockPostSearchRepository, times(0)).save(post);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setTitle(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkClientIdIsNotRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setClientId(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest + 1);
    }

    @Test
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setContent(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPublishedDateIsNotRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setPublishedDate(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest + 1);
    }

    @Test
    public void checkPublishedDateGMTIsNotRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setPublishedDateGMT(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest + 1);
    }

    @Test
    public void checkLastUpdatedDateIsNotRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setLastUpdatedDate(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest + 1);
    }

    @Test
    public void checkLastUpdatedDateGMTIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setLastUpdatedDateGMT(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest + 1);
    }

    @Test
    public void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setSlug(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc.perform(post("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPosts() throws Exception {
        post.setId("existing_id");
        PostDTO postDTO = postMapper.toDto(post);
        List<PostDTO> post1 = new ArrayList<>();
        post1.add(postDTO);
        PostResource postResource = new PostResource(postServiceMock, statusServiceMock);
        this.restPostMockMvc = MockMvcBuilders.standaloneSetup(postResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        when(postServiceMock.findByClientId(any(), any())).thenReturn(new PageImpl(post1));
        // Initialize the database
        postRepository.save(post);

        // Get all the postList
        restPostMockMvc.perform(get("/api/posts?sort=id,desc").requestAttr("ClientID", "testClientID"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].excerpt").value(hasItem(DEFAULT_EXCERPT.toString())))
            .andExpect(jsonPath("$.[*].publishedDate").value(hasItem(sameInstant(DEFAULT_PUBLISHED_DATE))))
            .andExpect(jsonPath("$.[*].publishedDateGMT").value(hasItem(sameInstant(DEFAULT_PUBLISHED_DATE_GMT))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDateGMT").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE_GMT))))
            .andExpect(jsonPath("$.[*].featured").value(hasItem(DEFAULT_FEATURED.booleanValue())))
            .andExpect(jsonPath("$.[*].sticky").value(hasItem(DEFAULT_STICKY.booleanValue())))
            .andExpect(jsonPath("$.[*].updates").value(hasItem(DEFAULT_UPDATES.toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].featuredMedia").value(hasItem(DEFAULT_FEATURED_MEDIA.toString())))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE.toString())));
    }

    public void getAllPostsWithEagerRelationshipsIsEnabled() throws Exception {
        PostResource postResource = new PostResource(postServiceMock, statusServiceMock);
        when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPostMockMvc = MockMvcBuilders.standaloneSetup(postResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPostMockMvc.perform(get("/api/posts?eagerload=true"))
        .andExpect(status().isOk());

        verify(postServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllPostsWithEagerRelationshipsIsNotEnabled() throws Exception {
        PostResource postResource = new PostResource(postServiceMock, statusServiceMock);
            when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPostMockMvc = MockMvcBuilders.standaloneSetup(postResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPostMockMvc.perform(get("/api/posts?eagerload=true"))
        .andExpect(status().isOk());

            verify(postServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getPost() throws Exception {
        // Initialize the database
        postRepository.save(post);

        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(post.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.excerpt").value(DEFAULT_EXCERPT.toString()))
            .andExpect(jsonPath("$.publishedDate").value(sameInstant(DEFAULT_PUBLISHED_DATE)))
            .andExpect(jsonPath("$.publishedDateGMT").value(sameInstant(DEFAULT_PUBLISHED_DATE_GMT)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDateGMT").value(sameInstant(DEFAULT_LAST_UPDATED_DATE_GMT)))
            .andExpect(jsonPath("$.featured").value(DEFAULT_FEATURED.booleanValue()))
            .andExpect(jsonPath("$.sticky").value(DEFAULT_STICKY.booleanValue()))
            .andExpect(jsonPath("$.updates").value(DEFAULT_UPDATES.toString()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.featuredMedia").value(DEFAULT_FEATURED_MEDIA.toString()))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE.toString()));
    }

    @Test
    public void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePost() throws Exception {
        // Initialize the database
        postRepository.save(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post
        Post updatedPost = postRepository.findById(post.getId()).get();
        updatedPost
            .title(UPDATED_TITLE)
            .clientId(UPDATED_CLIENT_ID)
            .content(UPDATED_CONTENT)
            .excerpt(UPDATED_EXCERPT)
            .publishedDate(UPDATED_PUBLISHED_DATE)
            .publishedDateGMT(UPDATED_PUBLISHED_DATE_GMT)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .lastUpdatedDateGMT(UPDATED_LAST_UPDATED_DATE_GMT)
            .featured(UPDATED_FEATURED)
            .sticky(UPDATED_STICKY)
            .updates(UPDATED_UPDATES)
            .slug(UPDATED_SLUG)
            .password(UPDATED_PASSWORD)
            .featuredMedia(UPDATED_FEATURED_MEDIA)
            .subTitle(UPDATED_SUB_TITLE);
        PostDTO postDTO = postMapper.toDto(updatedPost);

        restPostMockMvc.perform(put("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPost.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testPost.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPost.getExcerpt()).isEqualTo(UPDATED_EXCERPT);
        assertThat(testPost.getPublishedDate()).isEqualTo(UPDATED_PUBLISHED_DATE);
        assertThat(testPost.getPublishedDateGMT()).isEqualTo(UPDATED_PUBLISHED_DATE_GMT);
        assertThat(testPost.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testPost.getLastUpdatedDateGMT()).isEqualTo(UPDATED_LAST_UPDATED_DATE_GMT);
        assertThat(testPost.isFeatured()).isEqualTo(UPDATED_FEATURED);
        assertThat(testPost.isSticky()).isEqualTo(UPDATED_STICKY);
        assertThat(testPost.getUpdates()).isEqualTo(UPDATED_UPDATES);
        assertThat(testPost.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testPost.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPost.getFeaturedMedia()).isEqualTo(UPDATED_FEATURED_MEDIA);
        assertThat(testPost.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);

        // Validate the Post in Elasticsearch
        verify(mockPostSearchRepository, times(1)).save(testPost);
    }

    @Test
    public void updateNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc.perform(put("/api/posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Post in Elasticsearch
        verify(mockPostSearchRepository, times(0)).save(post);
    }

    @Test
    public void deletePost() throws Exception {
        // Initialize the database
        postRepository.save(post);

        int databaseSizeBeforeDelete = postRepository.findAll().size();

        // Get the post
        restPostMockMvc.perform(delete("/api/posts/{id}", post.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Post in Elasticsearch
        verify(mockPostSearchRepository, times(1)).deleteById(post.getId());
    }

    @Test
    public void searchPost() throws Exception {
        // Initialize the database
        postRepository.save(post);
        when(mockPostSearchRepository.search(queryStringQuery("id:" + post.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(post), PageRequest.of(0, 1), 1));
        // Search the post
        restPostMockMvc.perform(get("/api/_search/posts?query=id:" + post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].excerpt").value(hasItem(DEFAULT_EXCERPT.toString())))
            .andExpect(jsonPath("$.[*].publishedDate").value(hasItem(sameInstant(DEFAULT_PUBLISHED_DATE))))
            .andExpect(jsonPath("$.[*].publishedDateGMT").value(hasItem(sameInstant(DEFAULT_PUBLISHED_DATE_GMT))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDateGMT").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE_GMT))))
            .andExpect(jsonPath("$.[*].featured").value(hasItem(DEFAULT_FEATURED.booleanValue())))
            .andExpect(jsonPath("$.[*].sticky").value(hasItem(DEFAULT_STICKY.booleanValue())))
            .andExpect(jsonPath("$.[*].updates").value(hasItem(DEFAULT_UPDATES.toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].featuredMedia").value(hasItem(DEFAULT_FEATURED_MEDIA.toString())))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = new Post();
        post1.setId("id1");
        Post post2 = new Post();
        post2.setId(post1.getId());
        assertThat(post1).isEqualTo(post2);
        post2.setId("id2");
        assertThat(post1).isNotEqualTo(post2);
        post1.setId(null);
        assertThat(post1).isNotEqualTo(post2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostDTO.class);
        PostDTO postDTO1 = new PostDTO();
        postDTO1.setId("id1");
        PostDTO postDTO2 = new PostDTO();
        assertThat(postDTO1).isNotEqualTo(postDTO2);
        postDTO2.setId(postDTO1.getId());
        assertThat(postDTO1).isEqualTo(postDTO2);
        postDTO2.setId("id2");
        assertThat(postDTO1).isNotEqualTo(postDTO2);
        postDTO1.setId(null);
        assertThat(postDTO1).isNotEqualTo(postDTO2);
    }
}
