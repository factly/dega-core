package com.factly.dega.web.rest;

import com.factly.dega.CoreApp;

import com.factly.dega.domain.Tag;
import com.factly.dega.repository.TagRepository;
import com.factly.dega.repository.search.TagSearchRepository;
import com.factly.dega.service.TagService;
import com.factly.dega.service.dto.TagDTO;
import com.factly.dega.service.mapper.TagMapper;
import com.factly.dega.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
 * Test class for the TagResource REST controller.
 *
 * @see TagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class TagResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagService tagService;

    /**
     * This repository is mocked in the com.factly.dega.repository.search test package.
     *
     * @see com.factly.dega.repository.search.TagSearchRepositoryMockConfiguration
     */
    @Autowired
    private TagSearchRepository mockTagSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTagMockMvc;

    private Tag tag;

    @Mock
    private TagService tagServiceMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TagResource tagResource = new TagResource(tagService);
        this.restTagMockMvc = MockMvcBuilders.standaloneSetup(tagResource)
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
    public static Tag createEntity() {
        Tag tag = new Tag()
            .name(DEFAULT_NAME)
            .slug(DEFAULT_SLUG)
            .description(DEFAULT_DESCRIPTION)
            .clientId(DEFAULT_CLIENT_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE);
        return tag;
    }

    @Before
    public void initTest() {
        tagRepository.deleteAll();
        tag = createEntity();
    }

    @Test
    public void createTag() throws Exception {
        int databaseSizeBeforeCreate = tagRepository.findAll().size();

        // Create the Tag
        TagDTO tagDTO = tagMapper.toDto(tag);
        restTagMockMvc.perform(post("/api/tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isCreated());

        // Validate the Tag in the database
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeCreate + 1);
        Tag testTag = tagList.get(tagList.size() - 1);
        assertThat(testTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTag.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testTag.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTag.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testTag.getCreatedDate().toLocalDate()).isEqualTo(UPDATED_CREATED_DATE.toLocalDate());
        assertThat(testTag.getLastUpdatedDate().toLocalDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE.toLocalDate());

        // Validate the Tag in Elasticsearch
        verify(mockTagSearchRepository, times(1)).save(testTag);
    }

    @Test
    public void createTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagRepository.findAll().size();

        // Create the Tag with an existing ID
        tag.setId("existing_id");
        TagDTO tagDTO = tagMapper.toDto(tag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagMockMvc.perform(post("/api/tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tag in the database
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeCreate);

        // Validate the Tag in Elasticsearch
        verify(mockTagSearchRepository, times(0)).save(tag);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagRepository.findAll().size();
        // set the field null
        tag.setName(null);

        // Create the Tag, which fails.
        TagDTO tagDTO = tagMapper.toDto(tag);

        restTagMockMvc.perform(post("/api/tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isBadRequest());

        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagRepository.findAll().size();
        // set the field null
        tag.setSlug(null);

        // Create the Tag, which fails.
        TagDTO tagDTO = tagMapper.toDto(tag);

        restTagMockMvc.perform(post("/api/tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isBadRequest());

        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagRepository.findAll().size();
        // set the field null
        tag.setClientId(null);

        // Create the Tag, which fails.
        TagDTO tagDTO = tagMapper.toDto(tag);

        restTagMockMvc.perform(post("/api/tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isBadRequest());

        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagRepository.findAll().size();
        // set the field null
        tag.setCreatedDate(null);

        // Create the Tag, which fails.
        TagDTO tagDTO = tagMapper.toDto(tag);

        restTagMockMvc.perform(post("/api/tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isCreated());

        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeTest + 1);
    }

    @Test
    public void checkLastUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagRepository.findAll().size();
        // set the field null
        tag.setLastUpdatedDate(null);

        // Create the Tag, which fails.
        TagDTO tagDTO = tagMapper.toDto(tag);

        restTagMockMvc.perform(post("/api/tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isCreated());

        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeTest + 1);
    }

    @Test
    public void getAllTags() throws Exception {

        tag.setId("existing_id");
        TagDTO tagDTO = tagMapper.toDto(tag);
        List<TagDTO> tags = new ArrayList<>();
        tags.add(tagDTO);
        TagResource tagResource = new TagResource(tagServiceMock);
        this.restTagMockMvc = MockMvcBuilders.standaloneSetup(tagResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter).build();
        when(tagServiceMock.findByClientId(any(), any())).thenReturn(new PageImpl(tags));
        // Initialize the database
        tagRepository.save(tag);

        // Get all the tagList
        restTagMockMvc.perform(get("/api/tags?sort=id,desc").sessionAttr("ClientID", "testClientID"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tag.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));
    }

    @Test
    public void getTag() throws Exception {
        // Initialize the database
        tagRepository.save(tag);

        // Get the tag
        restTagMockMvc.perform(get("/api/tags/{id}", tag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tag.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)));
    }

    @Test
    public void getNonExistingTag() throws Exception {
        // Get the tag
        restTagMockMvc.perform(get("/api/tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTag() throws Exception {
        // Initialize the database
        tagRepository.save(tag);

        int databaseSizeBeforeUpdate = tagRepository.findAll().size();

        // Update the tag
        Tag updatedTag = tagRepository.findById(tag.getId()).get();
        updatedTag
            .name(UPDATED_NAME)
            .slug(UPDATED_SLUG)
            .description(UPDATED_DESCRIPTION)
            .clientId(UPDATED_CLIENT_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE);
        TagDTO tagDTO = tagMapper.toDto(updatedTag);

        restTagMockMvc.perform(put("/api/tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isOk());

        // Validate the Tag in the database
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeUpdate);
        Tag testTag = tagList.get(tagList.size() - 1);
        assertThat(testTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTag.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testTag.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTag.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testTag.getCreatedDate().toLocalDate()).isEqualTo(UPDATED_CREATED_DATE.toLocalDate());
        assertThat(testTag.getLastUpdatedDate().toLocalDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE.toLocalDate());

        // Validate the Tag in Elasticsearch
        verify(mockTagSearchRepository, times(1)).save(testTag);
    }

    @Test
    public void updateNonExistingTag() throws Exception {
        int databaseSizeBeforeUpdate = tagRepository.findAll().size();

        // Create the Tag
        TagDTO tagDTO = tagMapper.toDto(tag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagMockMvc.perform(put("/api/tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tag in the database
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Tag in Elasticsearch
        verify(mockTagSearchRepository, times(0)).save(tag);
    }

    @Test
    public void deleteTag() throws Exception {
        // Initialize the database
        tagRepository.save(tag);

        int databaseSizeBeforeDelete = tagRepository.findAll().size();

        // Get the tag
        restTagMockMvc.perform(delete("/api/tags/{id}", tag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Tag in Elasticsearch
        verify(mockTagSearchRepository, times(1)).deleteById(tag.getId());
    }

    @Test
    public void searchTag() throws Exception {
        // Initialize the database
        tagRepository.save(tag);
        when(mockTagSearchRepository.search(queryStringQuery("id:" + tag.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tag), PageRequest.of(0, 1), 1));
        // Search the tag
        restTagMockMvc.perform(get("/api/_search/tags?query=id:" + tag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tag.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tag.class);
        Tag tag1 = new Tag();
        tag1.setId("id1");
        Tag tag2 = new Tag();
        tag2.setId(tag1.getId());
        assertThat(tag1).isEqualTo(tag2);
        tag2.setId("id2");
        assertThat(tag1).isNotEqualTo(tag2);
        tag1.setId(null);
        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagDTO.class);
        TagDTO tagDTO1 = new TagDTO();
        tagDTO1.setId("id1");
        TagDTO tagDTO2 = new TagDTO();
        assertThat(tagDTO1).isNotEqualTo(tagDTO2);
        tagDTO2.setId(tagDTO1.getId());
        assertThat(tagDTO1).isEqualTo(tagDTO2);
        tagDTO2.setId("id2");
        assertThat(tagDTO1).isNotEqualTo(tagDTO2);
        tagDTO1.setId(null);
        assertThat(tagDTO1).isNotEqualTo(tagDTO2);
    }
}
