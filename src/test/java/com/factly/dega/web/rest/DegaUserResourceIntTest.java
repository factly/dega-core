package com.factly.dega.web.rest;

import com.factly.dega.CoreApp;

import com.factly.dega.domain.DegaUser;
import com.factly.dega.domain.Role;
import com.factly.dega.domain.Organization;
import com.factly.dega.domain.Organization;
import com.factly.dega.domain.Organization;
import com.factly.dega.domain.RoleMapping;
import com.factly.dega.repository.DegaUserRepository;
import com.factly.dega.repository.search.DegaUserSearchRepository;
import com.factly.dega.service.DegaUserService;
import com.factly.dega.service.dto.DegaUserDTO;
import com.factly.dega.service.mapper.DegaUserMapper;
import com.factly.dega.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
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
 * Test class for the DegaUserResource REST controller.
 *
 * @see DegaUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class DegaUserResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_URL = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_URL = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM_URL = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN_URL = "BBBBBBBBBB";

    private static final String DEFAULT_GITHUB_URL = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PICTURE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Boolean DEFAULT_EMAIL_VERIFIED = false;
    private static final Boolean UPDATED_EMAIL_VERIFIED = true;

    private static final String DEFAULT_EMAIL = "Pj@K5.hN";
    private static final String UPDATED_EMAIL = "5.@3.LtJ";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private DegaUserRepository degaUserRepository;

    @Mock
    private DegaUserRepository degaUserRepositoryMock;

    @Autowired
    private DegaUserMapper degaUserMapper;


    @Mock
    private DegaUserService degaUserServiceMock;

    @Autowired
    private DegaUserService degaUserService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String keycloakServerURI = "http://localhost:9080/auth/admin/realms/jhipster/users";

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private OAuth2Authentication oauth;

    @Mock
    private OAuth2AuthenticationDetails details;

    /**
     * This repository is mocked in the com.factly.dega.repository.search test package.
     *
     * @see com.factly.dega.repository.search.DegaUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private DegaUserSearchRepository mockDegaUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDegaUserMockMvc;

    private DegaUser degaUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DegaUserResource degaUserResource = new DegaUserResource(degaUserService, restTemplate, keycloakServerURI);
        this.restDegaUserMockMvc = MockMvcBuilders.standaloneSetup(degaUserResource)
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
    public static DegaUser createEntity() {
        DegaUser degaUser = new DegaUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .website(DEFAULT_WEBSITE)
            .facebookURL(DEFAULT_FACEBOOK_URL)
            .twitterURL(DEFAULT_TWITTER_URL)
            .instagramURL(DEFAULT_INSTAGRAM_URL)
            .linkedinURL(DEFAULT_LINKEDIN_URL)
            .githubURL(DEFAULT_GITHUB_URL)
            .profilePicture(DEFAULT_PROFILE_PICTURE)
            .description(DEFAULT_DESCRIPTION)
            .slug(DEFAULT_SLUG)
            .enabled(DEFAULT_ENABLED)
            .emailVerified(DEFAULT_EMAIL_VERIFIED)
            .email(DEFAULT_EMAIL)
            .createdDate(DEFAULT_CREATED_DATE);
        // Add required entity
        Role role = RoleResourceIntTest.createEntity();
        role.setId("fixed-id-for-tests");
        degaUser.setRole(role);
        // Add required entity
        Organization organization = OrganizationResourceIntTest.createEntity();
        organization.setId("fixed-id-for-tests");
        degaUser.getOrganizations().add(organization);
        // Add required entity
        degaUser.setOrganizationDefault(organization);
        // Add required entity
        degaUser.setOrganizationCurrent(organization);
        // Add required entity
        RoleMapping roleMapping = RoleMappingResourceIntTest.createEntity();
        roleMapping.setId("fixed-id-for-tests");
        degaUser.getRoleMappings().add(roleMapping);
        return degaUser;
    }

    @Before
    public void initTest() {
        degaUserRepository.deleteAll();
        degaUser = createEntity();
    }

    @Test
    @Ignore
    public void createDegaUser() throws Exception {
        int databaseSizeBeforeCreate = degaUserRepository.findAll().size();

        oauth.setDetails(new Object());

        // Create the DegaUser
        DegaUserDTO degaUserDTO = degaUserMapper.toDto(degaUser);
        when(restTemplateMock.postForObject(keycloakServerURI, Any.class, String.class)).thenReturn("");
        restDegaUserMockMvc.perform(post("/api/dega-users")
            .principal(oauth).contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degaUserDTO)))
            .andExpect(status().isCreated());

        // Validate the DegaUser in the database
        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeCreate + 1);
        DegaUser testDegaUser = degaUserList.get(degaUserList.size() - 1);
        assertThat(testDegaUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDegaUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDegaUser.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testDegaUser.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testDegaUser.getFacebookURL()).isEqualTo(DEFAULT_FACEBOOK_URL);
        assertThat(testDegaUser.getTwitterURL()).isEqualTo(DEFAULT_TWITTER_URL);
        assertThat(testDegaUser.getInstagramURL()).isEqualTo(DEFAULT_INSTAGRAM_URL);
        assertThat(testDegaUser.getLinkedinURL()).isEqualTo(DEFAULT_LINKEDIN_URL);
        assertThat(testDegaUser.getGithubURL()).isEqualTo(DEFAULT_GITHUB_URL);
        assertThat(testDegaUser.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
        assertThat(testDegaUser.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDegaUser.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testDegaUser.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testDegaUser.isEmailVerified()).isEqualTo(DEFAULT_EMAIL_VERIFIED);
        assertThat(testDegaUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDegaUser.getCreatedDate().toLocalDate()).isEqualTo(UPDATED_CREATED_DATE.toLocalDate());

        // Validate the DegaUser in Elasticsearch
        verify(mockDegaUserSearchRepository, times(1)).save(testDegaUser);
    }

    @Test
    public void createDegaUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = degaUserRepository.findAll().size();

        // Create the DegaUser with an existing ID
        degaUser.setId("existing_id");
        DegaUserDTO degaUserDTO = degaUserMapper.toDto(degaUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDegaUserMockMvc.perform(post("/api/dega-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degaUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DegaUser in the database
        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the DegaUser in Elasticsearch
        verify(mockDegaUserSearchRepository, times(0)).save(degaUser);
    }

    @Test
    public void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = degaUserRepository.findAll().size();
        // set the field null
        degaUser.setDisplayName(null);

        // Create the DegaUser, which fails.
        DegaUserDTO degaUserDTO = degaUserMapper.toDto(degaUser);

        restDegaUserMockMvc.perform(post("/api/dega-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degaUserDTO)))
            .andExpect(status().isBadRequest());

        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = degaUserRepository.findAll().size();
        // set the field null
        degaUser.setSlug(null);

        // Create the DegaUser, which fails.
        DegaUserDTO degaUserDTO = degaUserMapper.toDto(degaUser);

        restDegaUserMockMvc.perform(post("/api/dega-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degaUserDTO)))
            .andExpect(status().isBadRequest());

        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = degaUserRepository.findAll().size();
        // set the field null
        degaUser.setEnabled(null);

        // Create the DegaUser, which fails.
        DegaUserDTO degaUserDTO = degaUserMapper.toDto(degaUser);

        restDegaUserMockMvc.perform(post("/api/dega-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degaUserDTO)))
            .andExpect(status().isBadRequest());

        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = degaUserRepository.findAll().size();
        // set the field null
        degaUser.setEmail(null);

        // Create the DegaUser, which fails.
        DegaUserDTO degaUserDTO = degaUserMapper.toDto(degaUser);

        restDegaUserMockMvc.perform(post("/api/dega-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degaUserDTO)))
            .andExpect(status().isBadRequest());

        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Ignore
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = degaUserRepository.findAll().size();
        // set the field null
        degaUser.setCreatedDate(null);

        // Create the DegaUser, which fails.
        DegaUserDTO degaUserDTO = degaUserMapper.toDto(degaUser);

        restDegaUserMockMvc.perform(post("/api/dega-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degaUserDTO)))
            .andExpect(status().isCreated());

        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeTest + 1);
    }

    @Test
    public void getAllDegaUsers() throws Exception {
        // Initialize the database
        degaUserRepository.save(degaUser);

        // Get all the degaUserList
        restDegaUserMockMvc.perform(get("/api/dega-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(degaUser.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].facebookURL").value(hasItem(DEFAULT_FACEBOOK_URL.toString())))
            .andExpect(jsonPath("$.[*].twitterURL").value(hasItem(DEFAULT_TWITTER_URL.toString())))
            .andExpect(jsonPath("$.[*].instagramURL").value(hasItem(DEFAULT_INSTAGRAM_URL.toString())))
            .andExpect(jsonPath("$.[*].linkedinURL").value(hasItem(DEFAULT_LINKEDIN_URL.toString())))
            .andExpect(jsonPath("$.[*].githubURL").value(hasItem(DEFAULT_GITHUB_URL.toString())))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].emailVerified").value(hasItem(DEFAULT_EMAIL_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }

    public void getAllDegaUsersWithEagerRelationshipsIsEnabled() throws Exception {
        DegaUserResource degaUserResource = new DegaUserResource(degaUserServiceMock, restTemplateMock, keycloakServerURI);
        when(degaUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDegaUserMockMvc = MockMvcBuilders.standaloneSetup(degaUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDegaUserMockMvc.perform(get("/api/dega-users?eagerload=true"))
        .andExpect(status().isOk());

        verify(degaUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllDegaUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        DegaUserResource degaUserResource = new DegaUserResource(degaUserServiceMock, restTemplateMock, keycloakServerURI);
            when(degaUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDegaUserMockMvc = MockMvcBuilders.standaloneSetup(degaUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDegaUserMockMvc.perform(get("/api/dega-users?eagerload=true"))
        .andExpect(status().isOk());

            verify(degaUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getDegaUser() throws Exception {
        // Initialize the database
        degaUserRepository.save(degaUser);

        // Get the degaUser
        restDegaUserMockMvc.perform(get("/api/dega-users/{id}", degaUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(degaUser.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.facebookURL").value(DEFAULT_FACEBOOK_URL.toString()))
            .andExpect(jsonPath("$.twitterURL").value(DEFAULT_TWITTER_URL.toString()))
            .andExpect(jsonPath("$.instagramURL").value(DEFAULT_INSTAGRAM_URL.toString()))
            .andExpect(jsonPath("$.linkedinURL").value(DEFAULT_LINKEDIN_URL.toString()))
            .andExpect(jsonPath("$.githubURL").value(DEFAULT_GITHUB_URL.toString()))
            .andExpect(jsonPath("$.profilePicture").value(DEFAULT_PROFILE_PICTURE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.emailVerified").value(DEFAULT_EMAIL_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }

    @Test
    public void getNonExistingDegaUser() throws Exception {
        // Get the degaUser
        restDegaUserMockMvc.perform(get("/api/dega-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDegaUser() throws Exception {
        // Initialize the database
        degaUserRepository.save(degaUser);

        int databaseSizeBeforeUpdate = degaUserRepository.findAll().size();

        // Update the degaUser
        DegaUser updatedDegaUser = degaUserRepository.findById(degaUser.getId()).get();
        updatedDegaUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .website(UPDATED_WEBSITE)
            .facebookURL(UPDATED_FACEBOOK_URL)
            .twitterURL(UPDATED_TWITTER_URL)
            .instagramURL(UPDATED_INSTAGRAM_URL)
            .linkedinURL(UPDATED_LINKEDIN_URL)
            .githubURL(UPDATED_GITHUB_URL)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .description(UPDATED_DESCRIPTION)
            .slug(UPDATED_SLUG)
            .enabled(UPDATED_ENABLED)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .email(UPDATED_EMAIL)
            .createdDate(UPDATED_CREATED_DATE);
        DegaUserDTO degaUserDTO = degaUserMapper.toDto(updatedDegaUser);

        restDegaUserMockMvc.perform(put("/api/dega-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degaUserDTO)))
            .andExpect(status().isOk());

        // Validate the DegaUser in the database
        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeUpdate);
        DegaUser testDegaUser = degaUserList.get(degaUserList.size() - 1);
        assertThat(testDegaUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDegaUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDegaUser.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testDegaUser.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testDegaUser.getFacebookURL()).isEqualTo(UPDATED_FACEBOOK_URL);
        assertThat(testDegaUser.getTwitterURL()).isEqualTo(UPDATED_TWITTER_URL);
        assertThat(testDegaUser.getInstagramURL()).isEqualTo(UPDATED_INSTAGRAM_URL);
        assertThat(testDegaUser.getLinkedinURL()).isEqualTo(UPDATED_LINKEDIN_URL);
        assertThat(testDegaUser.getGithubURL()).isEqualTo(UPDATED_GITHUB_URL);
        assertThat(testDegaUser.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
        assertThat(testDegaUser.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDegaUser.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testDegaUser.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testDegaUser.isEmailVerified()).isEqualTo(UPDATED_EMAIL_VERIFIED);
        assertThat(testDegaUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDegaUser.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);

        // Validate the DegaUser in Elasticsearch
        verify(mockDegaUserSearchRepository, times(1)).save(testDegaUser);
    }

    @Test
    public void updateNonExistingDegaUser() throws Exception {
        int databaseSizeBeforeUpdate = degaUserRepository.findAll().size();

        // Create the DegaUser
        DegaUserDTO degaUserDTO = degaUserMapper.toDto(degaUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDegaUserMockMvc.perform(put("/api/dega-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(degaUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DegaUser in the database
        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DegaUser in Elasticsearch
        verify(mockDegaUserSearchRepository, times(0)).save(degaUser);
    }

    @Test
    public void deleteDegaUser() throws Exception {
        // Initialize the database
        degaUserRepository.save(degaUser);

        int databaseSizeBeforeDelete = degaUserRepository.findAll().size();

        // Get the degaUser
        restDegaUserMockMvc.perform(delete("/api/dega-users/{id}", degaUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DegaUser> degaUserList = degaUserRepository.findAll();
        assertThat(degaUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DegaUser in Elasticsearch
        verify(mockDegaUserSearchRepository, times(1)).deleteById(degaUser.getId());
    }

    @Test
    public void searchDegaUser() throws Exception {
        // Initialize the database
        degaUserRepository.save(degaUser);
        when(mockDegaUserSearchRepository.search(queryStringQuery("id:" + degaUser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(degaUser), PageRequest.of(0, 1), 1));
        // Search the degaUser
        restDegaUserMockMvc.perform(get("/api/_search/dega-users?query=id:" + degaUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(degaUser.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].facebookURL").value(hasItem(DEFAULT_FACEBOOK_URL.toString())))
            .andExpect(jsonPath("$.[*].twitterURL").value(hasItem(DEFAULT_TWITTER_URL.toString())))
            .andExpect(jsonPath("$.[*].instagramURL").value(hasItem(DEFAULT_INSTAGRAM_URL.toString())))
            .andExpect(jsonPath("$.[*].linkedinURL").value(hasItem(DEFAULT_LINKEDIN_URL.toString())))
            .andExpect(jsonPath("$.[*].githubURL").value(hasItem(DEFAULT_GITHUB_URL.toString())))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].emailVerified").value(hasItem(DEFAULT_EMAIL_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DegaUser.class);
        DegaUser degaUser1 = new DegaUser();
        degaUser1.setId("id1");
        DegaUser degaUser2 = new DegaUser();
        degaUser2.setId(degaUser1.getId());
        assertThat(degaUser1).isEqualTo(degaUser2);
        degaUser2.setId("id2");
        assertThat(degaUser1).isNotEqualTo(degaUser2);
        degaUser1.setId(null);
        assertThat(degaUser1).isNotEqualTo(degaUser2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DegaUserDTO.class);
        DegaUserDTO degaUserDTO1 = new DegaUserDTO();
        degaUserDTO1.setId("id1");
        DegaUserDTO degaUserDTO2 = new DegaUserDTO();
        assertThat(degaUserDTO1).isNotEqualTo(degaUserDTO2);
        degaUserDTO2.setId(degaUserDTO1.getId());
        assertThat(degaUserDTO1).isEqualTo(degaUserDTO2);
        degaUserDTO2.setId("id2");
        assertThat(degaUserDTO1).isNotEqualTo(degaUserDTO2);
        degaUserDTO1.setId(null);
        assertThat(degaUserDTO1).isNotEqualTo(degaUserDTO2);
    }
}
