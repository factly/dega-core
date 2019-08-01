package com.factly.dega.web.rest;

import com.factly.dega.CoreApp;

import com.factly.dega.domain.RoleMapping;
import com.factly.dega.domain.Organization;
import com.factly.dega.domain.Role;
import com.factly.dega.repository.RoleMappingRepository;
import com.factly.dega.repository.search.RoleMappingSearchRepository;
import com.factly.dega.service.RoleMappingService;
import com.factly.dega.service.dto.RoleMappingDTO;
import com.factly.dega.service.mapper.RoleMappingMapper;
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
 * Test class for the RoleMappingResource REST controller.
 *
 * @see RoleMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class RoleMappingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEYCLOAK_ID = "AAAAAAAAAA";
    private static final String UPDATED_KEYCLOAK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_KEYCLOAK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_KEYCLOAK_NAME = "BBBBBBBBBB";

    @Autowired
    private RoleMappingRepository roleMappingRepository;

    @Autowired
    private RoleMappingMapper roleMappingMapper;
    
    @Autowired
    private RoleMappingService roleMappingService;

    /**
     * This repository is mocked in the com.factly.dega.repository.search test package.
     *
     * @see com.factly.dega.repository.search.RoleMappingSearchRepositoryMockConfiguration
     */
    @Autowired
    private RoleMappingSearchRepository mockRoleMappingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRoleMappingMockMvc;

    private RoleMapping roleMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleMappingResource roleMappingResource = new RoleMappingResource(roleMappingService);
        this.restRoleMappingMockMvc = MockMvcBuilders.standaloneSetup(roleMappingResource)
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
    public static RoleMapping createEntity() {
        RoleMapping roleMapping = new RoleMapping()
            .name(DEFAULT_NAME)
            .keycloakId(DEFAULT_KEYCLOAK_ID)
            .keycloakName(DEFAULT_KEYCLOAK_NAME);
        // Add required entity
        Organization organization = OrganizationResourceIntTest.createEntity();
        organization.setId("fixed-id-for-tests");
        roleMapping.setOrganization(organization);
        // Add required entity
        Role role = RoleResourceIntTest.createEntity();
        role.setId("fixed-id-for-tests");
        roleMapping.setRole(role);
        return roleMapping;
    }

    @Before
    public void initTest() {
        roleMappingRepository.deleteAll();
        roleMapping = createEntity();
    }

    @Test
    public void createRoleMapping() throws Exception {
        int databaseSizeBeforeCreate = roleMappingRepository.findAll().size();

        // Create the RoleMapping
        RoleMappingDTO roleMappingDTO = roleMappingMapper.toDto(roleMapping);
        restRoleMappingMockMvc.perform(post("/api/role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the RoleMapping in the database
        List<RoleMapping> roleMappingList = roleMappingRepository.findAll();
        assertThat(roleMappingList).hasSize(databaseSizeBeforeCreate + 1);
        RoleMapping testRoleMapping = roleMappingList.get(roleMappingList.size() - 1);
        assertThat(testRoleMapping.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoleMapping.getKeycloakId()).isEqualTo(DEFAULT_KEYCLOAK_ID);
        assertThat(testRoleMapping.getKeycloakName()).isEqualTo(DEFAULT_KEYCLOAK_NAME);

        // Validate the RoleMapping in Elasticsearch
        verify(mockRoleMappingSearchRepository, times(1)).save(testRoleMapping);
    }

    @Test
    public void createRoleMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleMappingRepository.findAll().size();

        // Create the RoleMapping with an existing ID
        roleMapping.setId("existing_id");
        RoleMappingDTO roleMappingDTO = roleMappingMapper.toDto(roleMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleMappingMockMvc.perform(post("/api/role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoleMapping in the database
        List<RoleMapping> roleMappingList = roleMappingRepository.findAll();
        assertThat(roleMappingList).hasSize(databaseSizeBeforeCreate);

        // Validate the RoleMapping in Elasticsearch
        verify(mockRoleMappingSearchRepository, times(0)).save(roleMapping);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleMappingRepository.findAll().size();
        // set the field null
        roleMapping.setName(null);

        // Create the RoleMapping, which fails.
        RoleMappingDTO roleMappingDTO = roleMappingMapper.toDto(roleMapping);

        restRoleMappingMockMvc.perform(post("/api/role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMappingDTO)))
            .andExpect(status().isBadRequest());

        List<RoleMapping> roleMappingList = roleMappingRepository.findAll();
        assertThat(roleMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkKeycloakIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleMappingRepository.findAll().size();
        // set the field null
        roleMapping.setKeycloakId(null);

        // Create the RoleMapping, which fails.
        RoleMappingDTO roleMappingDTO = roleMappingMapper.toDto(roleMapping);

        restRoleMappingMockMvc.perform(post("/api/role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMappingDTO)))
            .andExpect(status().isBadRequest());

        List<RoleMapping> roleMappingList = roleMappingRepository.findAll();
        assertThat(roleMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkKeycloakNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleMappingRepository.findAll().size();
        // set the field null
        roleMapping.setKeycloakName(null);

        // Create the RoleMapping, which fails.
        RoleMappingDTO roleMappingDTO = roleMappingMapper.toDto(roleMapping);

        restRoleMappingMockMvc.perform(post("/api/role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMappingDTO)))
            .andExpect(status().isBadRequest());

        List<RoleMapping> roleMappingList = roleMappingRepository.findAll();
        assertThat(roleMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRoleMappings() throws Exception {
        // Initialize the database
        roleMappingRepository.save(roleMapping);

        // Get all the roleMappingList
        restRoleMappingMockMvc.perform(get("/api/role-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleMapping.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].keycloakId").value(hasItem(DEFAULT_KEYCLOAK_ID.toString())))
            .andExpect(jsonPath("$.[*].keycloakName").value(hasItem(DEFAULT_KEYCLOAK_NAME.toString())));
    }
    
    @Test
    public void getRoleMapping() throws Exception {
        // Initialize the database
        roleMappingRepository.save(roleMapping);

        // Get the roleMapping
        restRoleMappingMockMvc.perform(get("/api/role-mappings/{id}", roleMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roleMapping.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.keycloakId").value(DEFAULT_KEYCLOAK_ID.toString()))
            .andExpect(jsonPath("$.keycloakName").value(DEFAULT_KEYCLOAK_NAME.toString()));
    }

    @Test
    public void getNonExistingRoleMapping() throws Exception {
        // Get the roleMapping
        restRoleMappingMockMvc.perform(get("/api/role-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRoleMapping() throws Exception {
        // Initialize the database
        roleMappingRepository.save(roleMapping);

        int databaseSizeBeforeUpdate = roleMappingRepository.findAll().size();

        // Update the roleMapping
        RoleMapping updatedRoleMapping = roleMappingRepository.findById(roleMapping.getId()).get();
        updatedRoleMapping
            .name(UPDATED_NAME)
            .keycloakId(UPDATED_KEYCLOAK_ID)
            .keycloakName(UPDATED_KEYCLOAK_NAME);
        RoleMappingDTO roleMappingDTO = roleMappingMapper.toDto(updatedRoleMapping);

        restRoleMappingMockMvc.perform(put("/api/role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMappingDTO)))
            .andExpect(status().isOk());

        // Validate the RoleMapping in the database
        List<RoleMapping> roleMappingList = roleMappingRepository.findAll();
        assertThat(roleMappingList).hasSize(databaseSizeBeforeUpdate);
        RoleMapping testRoleMapping = roleMappingList.get(roleMappingList.size() - 1);
        assertThat(testRoleMapping.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoleMapping.getKeycloakId()).isEqualTo(UPDATED_KEYCLOAK_ID);
        assertThat(testRoleMapping.getKeycloakName()).isEqualTo(UPDATED_KEYCLOAK_NAME);

        // Validate the RoleMapping in Elasticsearch
        verify(mockRoleMappingSearchRepository, times(1)).save(testRoleMapping);
    }

    @Test
    public void updateNonExistingRoleMapping() throws Exception {
        int databaseSizeBeforeUpdate = roleMappingRepository.findAll().size();

        // Create the RoleMapping
        RoleMappingDTO roleMappingDTO = roleMappingMapper.toDto(roleMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleMappingMockMvc.perform(put("/api/role-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoleMapping in the database
        List<RoleMapping> roleMappingList = roleMappingRepository.findAll();
        assertThat(roleMappingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RoleMapping in Elasticsearch
        verify(mockRoleMappingSearchRepository, times(0)).save(roleMapping);
    }

    @Test
    public void deleteRoleMapping() throws Exception {
        // Initialize the database
        roleMappingRepository.save(roleMapping);

        int databaseSizeBeforeDelete = roleMappingRepository.findAll().size();

        // Get the roleMapping
        restRoleMappingMockMvc.perform(delete("/api/role-mappings/{id}", roleMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoleMapping> roleMappingList = roleMappingRepository.findAll();
        assertThat(roleMappingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RoleMapping in Elasticsearch
        verify(mockRoleMappingSearchRepository, times(1)).deleteById(roleMapping.getId());
    }

    @Test
    public void searchRoleMapping() throws Exception {
        // Initialize the database
        roleMappingRepository.save(roleMapping);
        when(mockRoleMappingSearchRepository.search(queryStringQuery("id:" + roleMapping.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(roleMapping), PageRequest.of(0, 1), 1));
        // Search the roleMapping
        restRoleMappingMockMvc.perform(get("/api/_search/role-mappings?query=id:" + roleMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleMapping.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].keycloakId").value(hasItem(DEFAULT_KEYCLOAK_ID.toString())))
            .andExpect(jsonPath("$.[*].keycloakName").value(hasItem(DEFAULT_KEYCLOAK_NAME.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleMapping.class);
        RoleMapping roleMapping1 = new RoleMapping();
        roleMapping1.setId("id1");
        RoleMapping roleMapping2 = new RoleMapping();
        roleMapping2.setId(roleMapping1.getId());
        assertThat(roleMapping1).isEqualTo(roleMapping2);
        roleMapping2.setId("id2");
        assertThat(roleMapping1).isNotEqualTo(roleMapping2);
        roleMapping1.setId(null);
        assertThat(roleMapping1).isNotEqualTo(roleMapping2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleMappingDTO.class);
        RoleMappingDTO roleMappingDTO1 = new RoleMappingDTO();
        roleMappingDTO1.setId("id1");
        RoleMappingDTO roleMappingDTO2 = new RoleMappingDTO();
        assertThat(roleMappingDTO1).isNotEqualTo(roleMappingDTO2);
        roleMappingDTO2.setId(roleMappingDTO1.getId());
        assertThat(roleMappingDTO1).isEqualTo(roleMappingDTO2);
        roleMappingDTO2.setId("id2");
        assertThat(roleMappingDTO1).isNotEqualTo(roleMappingDTO2);
        roleMappingDTO1.setId(null);
        assertThat(roleMappingDTO1).isNotEqualTo(roleMappingDTO2);
    }
}
