package com.factly.dega.web.rest;

import com.factly.dega.CoreApp;

import com.factly.dega.domain.Format;
import com.factly.dega.repository.FormatRepository;
import com.factly.dega.repository.search.FormatSearchRepository;
import com.factly.dega.service.FormatService;
import com.factly.dega.service.dto.FormatDTO;
import com.factly.dega.service.mapper.FormatMapper;
import com.factly.dega.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.util.Collections;
import java.util.List;


import static com.factly.dega.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FormatResource REST controller.
 *
 * @see FormatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class FormatResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    @Autowired
    private FormatRepository formatRepository;

    @Autowired
    private FormatMapper formatMapper;
    
    @Autowired
    private FormatService formatService;

    /**
     * This repository is mocked in the com.factly.dega.repository.search test package.
     *
     * @see com.factly.dega.repository.search.FormatSearchRepositoryMockConfiguration
     */
    @Autowired
    private FormatSearchRepository mockFormatSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFormatMockMvc;

    private Format format;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormatResource formatResource = new FormatResource(formatService);
        this.restFormatMockMvc = MockMvcBuilders.standaloneSetup(formatResource)
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
    public static Format createEntity() {
        Format format = new Format()
            .name(DEFAULT_NAME)
            .isDefault(DEFAULT_IS_DEFAULT)
            .clientId(DEFAULT_CLIENT_ID)
            .description(DEFAULT_DESCRIPTION)
            .slug(DEFAULT_SLUG);
        return format;
    }

    @Before
    public void initTest() {
        formatRepository.deleteAll();
        format = createEntity();
    }

    @Test
    public void createFormat() throws Exception {
        int databaseSizeBeforeCreate = formatRepository.findAll().size();

        // Create the Format
        FormatDTO formatDTO = formatMapper.toDto(format);
        restFormatMockMvc.perform(post("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isCreated());

        // Validate the Format in the database
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeCreate + 1);
        Format testFormat = formatList.get(formatList.size() - 1);
        assertThat(testFormat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormat.isIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testFormat.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testFormat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFormat.getSlug()).isEqualTo(DEFAULT_SLUG);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(1)).save(testFormat);
    }

    @Test
    public void createFormatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formatRepository.findAll().size();

        // Create the Format with an existing ID
        format.setId("existing_id");
        FormatDTO formatDTO = formatMapper.toDto(format);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormatMockMvc.perform(post("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Format in the database
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeCreate);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(0)).save(format);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = formatRepository.findAll().size();
        // set the field null
        format.setName(null);

        // Create the Format, which fails.
        FormatDTO formatDTO = formatMapper.toDto(format);

        restFormatMockMvc.perform(post("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isBadRequest());

        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = formatRepository.findAll().size();
        // set the field null
        format.setSlug(null);

        // Create the Format, which fails.
        FormatDTO formatDTO = formatMapper.toDto(format);

        restFormatMockMvc.perform(post("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isBadRequest());

        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFormats() throws Exception {
        // Initialize the database
        formatRepository.save(format);

        // Get all the formatList
        restFormatMockMvc.perform(get("/api/formats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(format.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())));
    }
    
    @Test
    public void getFormat() throws Exception {
        // Initialize the database
        formatRepository.save(format);

        // Get the format
        restFormatMockMvc.perform(get("/api/formats/{id}", format.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(format.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG.toString()));
    }

    @Test
    public void getNonExistingFormat() throws Exception {
        // Get the format
        restFormatMockMvc.perform(get("/api/formats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFormat() throws Exception {
        // Initialize the database
        formatRepository.save(format);

        int databaseSizeBeforeUpdate = formatRepository.findAll().size();

        // Update the format
        Format updatedFormat = formatRepository.findById(format.getId()).get();
        updatedFormat
            .name(UPDATED_NAME)
            .isDefault(UPDATED_IS_DEFAULT)
            .clientId(UPDATED_CLIENT_ID)
            .description(UPDATED_DESCRIPTION)
            .slug(UPDATED_SLUG);
        FormatDTO formatDTO = formatMapper.toDto(updatedFormat);

        restFormatMockMvc.perform(put("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isOk());

        // Validate the Format in the database
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeUpdate);
        Format testFormat = formatList.get(formatList.size() - 1);
        assertThat(testFormat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormat.isIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testFormat.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testFormat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFormat.getSlug()).isEqualTo(UPDATED_SLUG);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(1)).save(testFormat);
    }

    @Test
    public void updateNonExistingFormat() throws Exception {
        int databaseSizeBeforeUpdate = formatRepository.findAll().size();

        // Create the Format
        FormatDTO formatDTO = formatMapper.toDto(format);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormatMockMvc.perform(put("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Format in the database
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(0)).save(format);
    }

    @Test
    public void deleteFormat() throws Exception {
        // Initialize the database
        formatRepository.save(format);

        int databaseSizeBeforeDelete = formatRepository.findAll().size();

        // Get the format
        restFormatMockMvc.perform(delete("/api/formats/{id}", format.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(1)).deleteById(format.getId());
    }

    @Test
    public void searchFormat() throws Exception {
        // Initialize the database
        formatRepository.save(format);
        when(mockFormatSearchRepository.search(queryStringQuery("id:" + format.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(format), PageRequest.of(0, 1), 1));
        // Search the format
        restFormatMockMvc.perform(get("/api/_search/formats?query=id:" + format.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(format.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Format.class);
        Format format1 = new Format();
        format1.setId("id1");
        Format format2 = new Format();
        format2.setId(format1.getId());
        assertThat(format1).isEqualTo(format2);
        format2.setId("id2");
        assertThat(format1).isNotEqualTo(format2);
        format1.setId(null);
        assertThat(format1).isNotEqualTo(format2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormatDTO.class);
        FormatDTO formatDTO1 = new FormatDTO();
        formatDTO1.setId("id1");
        FormatDTO formatDTO2 = new FormatDTO();
        assertThat(formatDTO1).isNotEqualTo(formatDTO2);
        formatDTO2.setId(formatDTO1.getId());
        assertThat(formatDTO1).isEqualTo(formatDTO2);
        formatDTO2.setId("id2");
        assertThat(formatDTO1).isNotEqualTo(formatDTO2);
        formatDTO1.setId(null);
        assertThat(formatDTO1).isNotEqualTo(formatDTO2);
    }
}
