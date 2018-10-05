package com.factly.dega.web.rest;

import com.factly.dega.CoreApp;

import com.factly.dega.domain.Organization;
import com.factly.dega.repository.OrganizationRepository;
import com.factly.dega.repository.search.OrganizationSearchRepository;
import com.factly.dega.service.OrganizationService;
import com.factly.dega.service.dto.OrganizationDTO;
import com.factly.dega.service.mapper.OrganizationMapper;
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
 * Test class for the OrganizationResource REST controller.
 *
 * @see OrganizationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApp.class)
public class OrganizationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SITE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TAG_LINE = "AAAAAAAAAA";
    private static final String UPDATED_TAG_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_URL_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_URL_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_FAV_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_FAV_ICON_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_ICON_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BAIDU_VERIFICATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BAIDU_VERIFICATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BING_VERIFICATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BING_VERIFICATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_VERIFICATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_VERIFICATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_YANDEX_VERIFICATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_YANDEX_VERIFICATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_URL = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_URL = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM_URL = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_IN_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PINTEREST_URL = "AAAAAAAAAA";
    private static final String UPDATED_PINTEREST_URL = "BBBBBBBBBB";

    private static final String DEFAULT_YOU_TUBE_URL = "AAAAAAAAAA";
    private static final String UPDATED_YOU_TUBE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_PLUS_URL = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_PLUS_URL = "BBBBBBBBBB";

    private static final String DEFAULT_GITHUB_URL = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB_URL = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_PAGE_ACCESS_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_PAGE_ACCESS_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_GA_TRACKING_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GA_TRACKING_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GITHUB_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GITHUB_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB_CLIENT_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_CLIENT_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_CLIENT_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_CLIENT_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_IN_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_IN_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN_CLIENT_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM_CLIENT_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_MAILCHIMP_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_MAILCHIMP_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_SITE_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIME_ZONE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationMapper organizationMapper;
    
    @Autowired
    private OrganizationService organizationService;

    /**
     * This repository is mocked in the com.factly.dega.repository.search test package.
     *
     * @see com.factly.dega.repository.search.OrganizationSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrganizationSearchRepository mockOrganizationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationResource organizationResource = new OrganizationResource(organizationService);
        this.restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource)
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
    public static Organization createEntity() {
        Organization organization = new Organization()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .siteTitle(DEFAULT_SITE_TITLE)
            .tagLine(DEFAULT_TAG_LINE)
            .description(DEFAULT_DESCRIPTION)
            .logoURL(DEFAULT_LOGO_URL)
            .logoURLMobile(DEFAULT_LOGO_URL_MOBILE)
            .favIconURL(DEFAULT_FAV_ICON_URL)
            .mobileIconURL(DEFAULT_MOBILE_ICON_URL)
            .baiduVerificationCode(DEFAULT_BAIDU_VERIFICATION_CODE)
            .bingVerificationCode(DEFAULT_BING_VERIFICATION_CODE)
            .googleVerificationCode(DEFAULT_GOOGLE_VERIFICATION_CODE)
            .yandexVerificationCode(DEFAULT_YANDEX_VERIFICATION_CODE)
            .facebookURL(DEFAULT_FACEBOOK_URL)
            .twitterURL(DEFAULT_TWITTER_URL)
            .instagramURL(DEFAULT_INSTAGRAM_URL)
            .linkedInURL(DEFAULT_LINKED_IN_URL)
            .pinterestURL(DEFAULT_PINTEREST_URL)
            .youTubeURL(DEFAULT_YOU_TUBE_URL)
            .googlePlusURL(DEFAULT_GOOGLE_PLUS_URL)
            .githubURL(DEFAULT_GITHUB_URL)
            .facebookPageAccessToken(DEFAULT_FACEBOOK_PAGE_ACCESS_TOKEN)
            .gaTrackingCode(DEFAULT_GA_TRACKING_CODE)
            .githubClientId(DEFAULT_GITHUB_CLIENT_ID)
            .githubClientSecret(DEFAULT_GITHUB_CLIENT_SECRET)
            .twitterClientId(DEFAULT_TWITTER_CLIENT_ID)
            .twitterClientSecret(DEFAULT_TWITTER_CLIENT_SECRET)
            .facebookClientId(DEFAULT_FACEBOOK_CLIENT_ID)
            .facebookClientSecret(DEFAULT_FACEBOOK_CLIENT_SECRET)
            .googleClientId(DEFAULT_GOOGLE_CLIENT_ID)
            .googleClientSecret(DEFAULT_GOOGLE_CLIENT_SECRET)
            .linkedInClientId(DEFAULT_LINKED_IN_CLIENT_ID)
            .linkedInClientSecret(DEFAULT_LINKED_IN_CLIENT_SECRET)
            .instagramClientId(DEFAULT_INSTAGRAM_CLIENT_ID)
            .instagramClientSecret(DEFAULT_INSTAGRAM_CLIENT_SECRET)
            .mailchimpAPIKey(DEFAULT_MAILCHIMP_API_KEY)
            .siteLanguage(DEFAULT_SITE_LANGUAGE)
            .timeZone(DEFAULT_TIME_ZONE)
            .clientId(DEFAULT_CLIENT_ID);
        return organization;
    }

    @Before
    public void initTest() {
        organizationRepository.deleteAll();
        organization = createEntity();
    }

    @Test
    public void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganization.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganization.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOrganization.getSiteTitle()).isEqualTo(DEFAULT_SITE_TITLE);
        assertThat(testOrganization.getTagLine()).isEqualTo(DEFAULT_TAG_LINE);
        assertThat(testOrganization.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganization.getLogoURL()).isEqualTo(DEFAULT_LOGO_URL);
        assertThat(testOrganization.getLogoURLMobile()).isEqualTo(DEFAULT_LOGO_URL_MOBILE);
        assertThat(testOrganization.getFavIconURL()).isEqualTo(DEFAULT_FAV_ICON_URL);
        assertThat(testOrganization.getMobileIconURL()).isEqualTo(DEFAULT_MOBILE_ICON_URL);
        assertThat(testOrganization.getBaiduVerificationCode()).isEqualTo(DEFAULT_BAIDU_VERIFICATION_CODE);
        assertThat(testOrganization.getBingVerificationCode()).isEqualTo(DEFAULT_BING_VERIFICATION_CODE);
        assertThat(testOrganization.getGoogleVerificationCode()).isEqualTo(DEFAULT_GOOGLE_VERIFICATION_CODE);
        assertThat(testOrganization.getYandexVerificationCode()).isEqualTo(DEFAULT_YANDEX_VERIFICATION_CODE);
        assertThat(testOrganization.getFacebookURL()).isEqualTo(DEFAULT_FACEBOOK_URL);
        assertThat(testOrganization.getTwitterURL()).isEqualTo(DEFAULT_TWITTER_URL);
        assertThat(testOrganization.getInstagramURL()).isEqualTo(DEFAULT_INSTAGRAM_URL);
        assertThat(testOrganization.getLinkedInURL()).isEqualTo(DEFAULT_LINKED_IN_URL);
        assertThat(testOrganization.getPinterestURL()).isEqualTo(DEFAULT_PINTEREST_URL);
        assertThat(testOrganization.getYouTubeURL()).isEqualTo(DEFAULT_YOU_TUBE_URL);
        assertThat(testOrganization.getGooglePlusURL()).isEqualTo(DEFAULT_GOOGLE_PLUS_URL);
        assertThat(testOrganization.getGithubURL()).isEqualTo(DEFAULT_GITHUB_URL);
        assertThat(testOrganization.getFacebookPageAccessToken()).isEqualTo(DEFAULT_FACEBOOK_PAGE_ACCESS_TOKEN);
        assertThat(testOrganization.getGaTrackingCode()).isEqualTo(DEFAULT_GA_TRACKING_CODE);
        assertThat(testOrganization.getGithubClientId()).isEqualTo(DEFAULT_GITHUB_CLIENT_ID);
        assertThat(testOrganization.getGithubClientSecret()).isEqualTo(DEFAULT_GITHUB_CLIENT_SECRET);
        assertThat(testOrganization.getTwitterClientId()).isEqualTo(DEFAULT_TWITTER_CLIENT_ID);
        assertThat(testOrganization.getTwitterClientSecret()).isEqualTo(DEFAULT_TWITTER_CLIENT_SECRET);
        assertThat(testOrganization.getFacebookClientId()).isEqualTo(DEFAULT_FACEBOOK_CLIENT_ID);
        assertThat(testOrganization.getFacebookClientSecret()).isEqualTo(DEFAULT_FACEBOOK_CLIENT_SECRET);
        assertThat(testOrganization.getGoogleClientId()).isEqualTo(DEFAULT_GOOGLE_CLIENT_ID);
        assertThat(testOrganization.getGoogleClientSecret()).isEqualTo(DEFAULT_GOOGLE_CLIENT_SECRET);
        assertThat(testOrganization.getLinkedInClientId()).isEqualTo(DEFAULT_LINKED_IN_CLIENT_ID);
        assertThat(testOrganization.getLinkedInClientSecret()).isEqualTo(DEFAULT_LINKED_IN_CLIENT_SECRET);
        assertThat(testOrganization.getInstagramClientId()).isEqualTo(DEFAULT_INSTAGRAM_CLIENT_ID);
        assertThat(testOrganization.getInstagramClientSecret()).isEqualTo(DEFAULT_INSTAGRAM_CLIENT_SECRET);
        assertThat(testOrganization.getMailchimpAPIKey()).isEqualTo(DEFAULT_MAILCHIMP_API_KEY);
        assertThat(testOrganization.getSiteLanguage()).isEqualTo(DEFAULT_SITE_LANGUAGE);
        assertThat(testOrganization.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);
        assertThat(testOrganization.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);

        // Validate the Organization in Elasticsearch
        verify(mockOrganizationSearchRepository, times(1)).save(testOrganization);
    }

    @Test
    public void createOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization with an existing ID
        organization.setId("existing_id");
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Organization in Elasticsearch
        verify(mockOrganizationSearchRepository, times(0)).save(organization);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setName(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setEmail(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSiteTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setSiteTitle(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setClientId(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        // Get all the organizationList
        restOrganizationMockMvc.perform(get("/api/organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].siteTitle").value(hasItem(DEFAULT_SITE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].tagLine").value(hasItem(DEFAULT_TAG_LINE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].logoURL").value(hasItem(DEFAULT_LOGO_URL.toString())))
            .andExpect(jsonPath("$.[*].logoURLMobile").value(hasItem(DEFAULT_LOGO_URL_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].favIconURL").value(hasItem(DEFAULT_FAV_ICON_URL.toString())))
            .andExpect(jsonPath("$.[*].mobileIconURL").value(hasItem(DEFAULT_MOBILE_ICON_URL.toString())))
            .andExpect(jsonPath("$.[*].baiduVerificationCode").value(hasItem(DEFAULT_BAIDU_VERIFICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].bingVerificationCode").value(hasItem(DEFAULT_BING_VERIFICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].googleVerificationCode").value(hasItem(DEFAULT_GOOGLE_VERIFICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].yandexVerificationCode").value(hasItem(DEFAULT_YANDEX_VERIFICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].facebookURL").value(hasItem(DEFAULT_FACEBOOK_URL.toString())))
            .andExpect(jsonPath("$.[*].twitterURL").value(hasItem(DEFAULT_TWITTER_URL.toString())))
            .andExpect(jsonPath("$.[*].instagramURL").value(hasItem(DEFAULT_INSTAGRAM_URL.toString())))
            .andExpect(jsonPath("$.[*].linkedInURL").value(hasItem(DEFAULT_LINKED_IN_URL.toString())))
            .andExpect(jsonPath("$.[*].pinterestURL").value(hasItem(DEFAULT_PINTEREST_URL.toString())))
            .andExpect(jsonPath("$.[*].youTubeURL").value(hasItem(DEFAULT_YOU_TUBE_URL.toString())))
            .andExpect(jsonPath("$.[*].googlePlusURL").value(hasItem(DEFAULT_GOOGLE_PLUS_URL.toString())))
            .andExpect(jsonPath("$.[*].githubURL").value(hasItem(DEFAULT_GITHUB_URL.toString())))
            .andExpect(jsonPath("$.[*].facebookPageAccessToken").value(hasItem(DEFAULT_FACEBOOK_PAGE_ACCESS_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].gaTrackingCode").value(hasItem(DEFAULT_GA_TRACKING_CODE.toString())))
            .andExpect(jsonPath("$.[*].githubClientId").value(hasItem(DEFAULT_GITHUB_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].githubClientSecret").value(hasItem(DEFAULT_GITHUB_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].twitterClientId").value(hasItem(DEFAULT_TWITTER_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].twitterClientSecret").value(hasItem(DEFAULT_TWITTER_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].facebookClientId").value(hasItem(DEFAULT_FACEBOOK_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].facebookClientSecret").value(hasItem(DEFAULT_FACEBOOK_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].googleClientId").value(hasItem(DEFAULT_GOOGLE_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].googleClientSecret").value(hasItem(DEFAULT_GOOGLE_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].linkedInClientId").value(hasItem(DEFAULT_LINKED_IN_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].linkedInClientSecret").value(hasItem(DEFAULT_LINKED_IN_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].instagramClientId").value(hasItem(DEFAULT_INSTAGRAM_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].instagramClientSecret").value(hasItem(DEFAULT_INSTAGRAM_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].mailchimpAPIKey").value(hasItem(DEFAULT_MAILCHIMP_API_KEY.toString())))
            .andExpect(jsonPath("$.[*].siteLanguage").value(hasItem(DEFAULT_SITE_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())));
    }
    
    @Test
    public void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.siteTitle").value(DEFAULT_SITE_TITLE.toString()))
            .andExpect(jsonPath("$.tagLine").value(DEFAULT_TAG_LINE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.logoURL").value(DEFAULT_LOGO_URL.toString()))
            .andExpect(jsonPath("$.logoURLMobile").value(DEFAULT_LOGO_URL_MOBILE.toString()))
            .andExpect(jsonPath("$.favIconURL").value(DEFAULT_FAV_ICON_URL.toString()))
            .andExpect(jsonPath("$.mobileIconURL").value(DEFAULT_MOBILE_ICON_URL.toString()))
            .andExpect(jsonPath("$.baiduVerificationCode").value(DEFAULT_BAIDU_VERIFICATION_CODE.toString()))
            .andExpect(jsonPath("$.bingVerificationCode").value(DEFAULT_BING_VERIFICATION_CODE.toString()))
            .andExpect(jsonPath("$.googleVerificationCode").value(DEFAULT_GOOGLE_VERIFICATION_CODE.toString()))
            .andExpect(jsonPath("$.yandexVerificationCode").value(DEFAULT_YANDEX_VERIFICATION_CODE.toString()))
            .andExpect(jsonPath("$.facebookURL").value(DEFAULT_FACEBOOK_URL.toString()))
            .andExpect(jsonPath("$.twitterURL").value(DEFAULT_TWITTER_URL.toString()))
            .andExpect(jsonPath("$.instagramURL").value(DEFAULT_INSTAGRAM_URL.toString()))
            .andExpect(jsonPath("$.linkedInURL").value(DEFAULT_LINKED_IN_URL.toString()))
            .andExpect(jsonPath("$.pinterestURL").value(DEFAULT_PINTEREST_URL.toString()))
            .andExpect(jsonPath("$.youTubeURL").value(DEFAULT_YOU_TUBE_URL.toString()))
            .andExpect(jsonPath("$.googlePlusURL").value(DEFAULT_GOOGLE_PLUS_URL.toString()))
            .andExpect(jsonPath("$.githubURL").value(DEFAULT_GITHUB_URL.toString()))
            .andExpect(jsonPath("$.facebookPageAccessToken").value(DEFAULT_FACEBOOK_PAGE_ACCESS_TOKEN.toString()))
            .andExpect(jsonPath("$.gaTrackingCode").value(DEFAULT_GA_TRACKING_CODE.toString()))
            .andExpect(jsonPath("$.githubClientId").value(DEFAULT_GITHUB_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.githubClientSecret").value(DEFAULT_GITHUB_CLIENT_SECRET.toString()))
            .andExpect(jsonPath("$.twitterClientId").value(DEFAULT_TWITTER_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.twitterClientSecret").value(DEFAULT_TWITTER_CLIENT_SECRET.toString()))
            .andExpect(jsonPath("$.facebookClientId").value(DEFAULT_FACEBOOK_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.facebookClientSecret").value(DEFAULT_FACEBOOK_CLIENT_SECRET.toString()))
            .andExpect(jsonPath("$.googleClientId").value(DEFAULT_GOOGLE_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.googleClientSecret").value(DEFAULT_GOOGLE_CLIENT_SECRET.toString()))
            .andExpect(jsonPath("$.linkedInClientId").value(DEFAULT_LINKED_IN_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.linkedInClientSecret").value(DEFAULT_LINKED_IN_CLIENT_SECRET.toString()))
            .andExpect(jsonPath("$.instagramClientId").value(DEFAULT_INSTAGRAM_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.instagramClientSecret").value(DEFAULT_INSTAGRAM_CLIENT_SECRET.toString()))
            .andExpect(jsonPath("$.mailchimpAPIKey").value(DEFAULT_MAILCHIMP_API_KEY.toString()))
            .andExpect(jsonPath("$.siteLanguage").value(DEFAULT_SITE_LANGUAGE.toString()))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE.toString()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()));
    }

    @Test
    public void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrganization() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).get();
        updatedOrganization
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .siteTitle(UPDATED_SITE_TITLE)
            .tagLine(UPDATED_TAG_LINE)
            .description(UPDATED_DESCRIPTION)
            .logoURL(UPDATED_LOGO_URL)
            .logoURLMobile(UPDATED_LOGO_URL_MOBILE)
            .favIconURL(UPDATED_FAV_ICON_URL)
            .mobileIconURL(UPDATED_MOBILE_ICON_URL)
            .baiduVerificationCode(UPDATED_BAIDU_VERIFICATION_CODE)
            .bingVerificationCode(UPDATED_BING_VERIFICATION_CODE)
            .googleVerificationCode(UPDATED_GOOGLE_VERIFICATION_CODE)
            .yandexVerificationCode(UPDATED_YANDEX_VERIFICATION_CODE)
            .facebookURL(UPDATED_FACEBOOK_URL)
            .twitterURL(UPDATED_TWITTER_URL)
            .instagramURL(UPDATED_INSTAGRAM_URL)
            .linkedInURL(UPDATED_LINKED_IN_URL)
            .pinterestURL(UPDATED_PINTEREST_URL)
            .youTubeURL(UPDATED_YOU_TUBE_URL)
            .googlePlusURL(UPDATED_GOOGLE_PLUS_URL)
            .githubURL(UPDATED_GITHUB_URL)
            .facebookPageAccessToken(UPDATED_FACEBOOK_PAGE_ACCESS_TOKEN)
            .gaTrackingCode(UPDATED_GA_TRACKING_CODE)
            .githubClientId(UPDATED_GITHUB_CLIENT_ID)
            .githubClientSecret(UPDATED_GITHUB_CLIENT_SECRET)
            .twitterClientId(UPDATED_TWITTER_CLIENT_ID)
            .twitterClientSecret(UPDATED_TWITTER_CLIENT_SECRET)
            .facebookClientId(UPDATED_FACEBOOK_CLIENT_ID)
            .facebookClientSecret(UPDATED_FACEBOOK_CLIENT_SECRET)
            .googleClientId(UPDATED_GOOGLE_CLIENT_ID)
            .googleClientSecret(UPDATED_GOOGLE_CLIENT_SECRET)
            .linkedInClientId(UPDATED_LINKED_IN_CLIENT_ID)
            .linkedInClientSecret(UPDATED_LINKED_IN_CLIENT_SECRET)
            .instagramClientId(UPDATED_INSTAGRAM_CLIENT_ID)
            .instagramClientSecret(UPDATED_INSTAGRAM_CLIENT_SECRET)
            .mailchimpAPIKey(UPDATED_MAILCHIMP_API_KEY)
            .siteLanguage(UPDATED_SITE_LANGUAGE)
            .timeZone(UPDATED_TIME_ZONE)
            .clientId(UPDATED_CLIENT_ID);
        OrganizationDTO organizationDTO = organizationMapper.toDto(updatedOrganization);

        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganization.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrganization.getSiteTitle()).isEqualTo(UPDATED_SITE_TITLE);
        assertThat(testOrganization.getTagLine()).isEqualTo(UPDATED_TAG_LINE);
        assertThat(testOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganization.getLogoURL()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testOrganization.getLogoURLMobile()).isEqualTo(UPDATED_LOGO_URL_MOBILE);
        assertThat(testOrganization.getFavIconURL()).isEqualTo(UPDATED_FAV_ICON_URL);
        assertThat(testOrganization.getMobileIconURL()).isEqualTo(UPDATED_MOBILE_ICON_URL);
        assertThat(testOrganization.getBaiduVerificationCode()).isEqualTo(UPDATED_BAIDU_VERIFICATION_CODE);
        assertThat(testOrganization.getBingVerificationCode()).isEqualTo(UPDATED_BING_VERIFICATION_CODE);
        assertThat(testOrganization.getGoogleVerificationCode()).isEqualTo(UPDATED_GOOGLE_VERIFICATION_CODE);
        assertThat(testOrganization.getYandexVerificationCode()).isEqualTo(UPDATED_YANDEX_VERIFICATION_CODE);
        assertThat(testOrganization.getFacebookURL()).isEqualTo(UPDATED_FACEBOOK_URL);
        assertThat(testOrganization.getTwitterURL()).isEqualTo(UPDATED_TWITTER_URL);
        assertThat(testOrganization.getInstagramURL()).isEqualTo(UPDATED_INSTAGRAM_URL);
        assertThat(testOrganization.getLinkedInURL()).isEqualTo(UPDATED_LINKED_IN_URL);
        assertThat(testOrganization.getPinterestURL()).isEqualTo(UPDATED_PINTEREST_URL);
        assertThat(testOrganization.getYouTubeURL()).isEqualTo(UPDATED_YOU_TUBE_URL);
        assertThat(testOrganization.getGooglePlusURL()).isEqualTo(UPDATED_GOOGLE_PLUS_URL);
        assertThat(testOrganization.getGithubURL()).isEqualTo(UPDATED_GITHUB_URL);
        assertThat(testOrganization.getFacebookPageAccessToken()).isEqualTo(UPDATED_FACEBOOK_PAGE_ACCESS_TOKEN);
        assertThat(testOrganization.getGaTrackingCode()).isEqualTo(UPDATED_GA_TRACKING_CODE);
        assertThat(testOrganization.getGithubClientId()).isEqualTo(UPDATED_GITHUB_CLIENT_ID);
        assertThat(testOrganization.getGithubClientSecret()).isEqualTo(UPDATED_GITHUB_CLIENT_SECRET);
        assertThat(testOrganization.getTwitterClientId()).isEqualTo(UPDATED_TWITTER_CLIENT_ID);
        assertThat(testOrganization.getTwitterClientSecret()).isEqualTo(UPDATED_TWITTER_CLIENT_SECRET);
        assertThat(testOrganization.getFacebookClientId()).isEqualTo(UPDATED_FACEBOOK_CLIENT_ID);
        assertThat(testOrganization.getFacebookClientSecret()).isEqualTo(UPDATED_FACEBOOK_CLIENT_SECRET);
        assertThat(testOrganization.getGoogleClientId()).isEqualTo(UPDATED_GOOGLE_CLIENT_ID);
        assertThat(testOrganization.getGoogleClientSecret()).isEqualTo(UPDATED_GOOGLE_CLIENT_SECRET);
        assertThat(testOrganization.getLinkedInClientId()).isEqualTo(UPDATED_LINKED_IN_CLIENT_ID);
        assertThat(testOrganization.getLinkedInClientSecret()).isEqualTo(UPDATED_LINKED_IN_CLIENT_SECRET);
        assertThat(testOrganization.getInstagramClientId()).isEqualTo(UPDATED_INSTAGRAM_CLIENT_ID);
        assertThat(testOrganization.getInstagramClientSecret()).isEqualTo(UPDATED_INSTAGRAM_CLIENT_SECRET);
        assertThat(testOrganization.getMailchimpAPIKey()).isEqualTo(UPDATED_MAILCHIMP_API_KEY);
        assertThat(testOrganization.getSiteLanguage()).isEqualTo(UPDATED_SITE_LANGUAGE);
        assertThat(testOrganization.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);
        assertThat(testOrganization.getClientId()).isEqualTo(UPDATED_CLIENT_ID);

        // Validate the Organization in Elasticsearch
        verify(mockOrganizationSearchRepository, times(1)).save(testOrganization);
    }

    @Test
    public void updateNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Organization in Elasticsearch
        verify(mockOrganizationSearchRepository, times(0)).save(organization);
    }

    @Test
    public void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Get the organization
        restOrganizationMockMvc.perform(delete("/api/organizations/{id}", organization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Organization in Elasticsearch
        verify(mockOrganizationSearchRepository, times(1)).deleteById(organization.getId());
    }

    @Test
    public void searchOrganization() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);
        when(mockOrganizationSearchRepository.search(queryStringQuery("id:" + organization.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(organization), PageRequest.of(0, 1), 1));
        // Search the organization
        restOrganizationMockMvc.perform(get("/api/_search/organizations?query=id:" + organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].siteTitle").value(hasItem(DEFAULT_SITE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].tagLine").value(hasItem(DEFAULT_TAG_LINE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].logoURL").value(hasItem(DEFAULT_LOGO_URL.toString())))
            .andExpect(jsonPath("$.[*].logoURLMobile").value(hasItem(DEFAULT_LOGO_URL_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].favIconURL").value(hasItem(DEFAULT_FAV_ICON_URL.toString())))
            .andExpect(jsonPath("$.[*].mobileIconURL").value(hasItem(DEFAULT_MOBILE_ICON_URL.toString())))
            .andExpect(jsonPath("$.[*].baiduVerificationCode").value(hasItem(DEFAULT_BAIDU_VERIFICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].bingVerificationCode").value(hasItem(DEFAULT_BING_VERIFICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].googleVerificationCode").value(hasItem(DEFAULT_GOOGLE_VERIFICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].yandexVerificationCode").value(hasItem(DEFAULT_YANDEX_VERIFICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].facebookURL").value(hasItem(DEFAULT_FACEBOOK_URL.toString())))
            .andExpect(jsonPath("$.[*].twitterURL").value(hasItem(DEFAULT_TWITTER_URL.toString())))
            .andExpect(jsonPath("$.[*].instagramURL").value(hasItem(DEFAULT_INSTAGRAM_URL.toString())))
            .andExpect(jsonPath("$.[*].linkedInURL").value(hasItem(DEFAULT_LINKED_IN_URL.toString())))
            .andExpect(jsonPath("$.[*].pinterestURL").value(hasItem(DEFAULT_PINTEREST_URL.toString())))
            .andExpect(jsonPath("$.[*].youTubeURL").value(hasItem(DEFAULT_YOU_TUBE_URL.toString())))
            .andExpect(jsonPath("$.[*].googlePlusURL").value(hasItem(DEFAULT_GOOGLE_PLUS_URL.toString())))
            .andExpect(jsonPath("$.[*].githubURL").value(hasItem(DEFAULT_GITHUB_URL.toString())))
            .andExpect(jsonPath("$.[*].facebookPageAccessToken").value(hasItem(DEFAULT_FACEBOOK_PAGE_ACCESS_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].gaTrackingCode").value(hasItem(DEFAULT_GA_TRACKING_CODE.toString())))
            .andExpect(jsonPath("$.[*].githubClientId").value(hasItem(DEFAULT_GITHUB_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].githubClientSecret").value(hasItem(DEFAULT_GITHUB_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].twitterClientId").value(hasItem(DEFAULT_TWITTER_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].twitterClientSecret").value(hasItem(DEFAULT_TWITTER_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].facebookClientId").value(hasItem(DEFAULT_FACEBOOK_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].facebookClientSecret").value(hasItem(DEFAULT_FACEBOOK_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].googleClientId").value(hasItem(DEFAULT_GOOGLE_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].googleClientSecret").value(hasItem(DEFAULT_GOOGLE_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].linkedInClientId").value(hasItem(DEFAULT_LINKED_IN_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].linkedInClientSecret").value(hasItem(DEFAULT_LINKED_IN_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].instagramClientId").value(hasItem(DEFAULT_INSTAGRAM_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].instagramClientSecret").value(hasItem(DEFAULT_INSTAGRAM_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].mailchimpAPIKey").value(hasItem(DEFAULT_MAILCHIMP_API_KEY.toString())))
            .andExpect(jsonPath("$.[*].siteLanguage").value(hasItem(DEFAULT_SITE_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organization.class);
        Organization organization1 = new Organization();
        organization1.setId("id1");
        Organization organization2 = new Organization();
        organization2.setId(organization1.getId());
        assertThat(organization1).isEqualTo(organization2);
        organization2.setId("id2");
        assertThat(organization1).isNotEqualTo(organization2);
        organization1.setId(null);
        assertThat(organization1).isNotEqualTo(organization2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationDTO.class);
        OrganizationDTO organizationDTO1 = new OrganizationDTO();
        organizationDTO1.setId("id1");
        OrganizationDTO organizationDTO2 = new OrganizationDTO();
        assertThat(organizationDTO1).isNotEqualTo(organizationDTO2);
        organizationDTO2.setId(organizationDTO1.getId());
        assertThat(organizationDTO1).isEqualTo(organizationDTO2);
        organizationDTO2.setId("id2");
        assertThat(organizationDTO1).isNotEqualTo(organizationDTO2);
        organizationDTO1.setId(null);
        assertThat(organizationDTO1).isNotEqualTo(organizationDTO2);
    }
}
