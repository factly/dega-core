package com.factly.dega.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Organization.
 */
@Document(collection = "organization")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("phone")
    private String phone;

    @NotNull
    @Field("site_title")
    private String siteTitle;

    @Field("tag_line")
    private String tagLine;

    @Field("description")
    private String description;

    @Field("logo_url")
    private String logoURL;

    @Field("logo_url_mobile")
    private String logoURLMobile;

    @Field("fav_icon_url")
    private String favIconURL;

    @Field("mobile_icon_url")
    private String mobileIconURL;

    @Field("baidu_verification_code")
    private String baiduVerificationCode;

    @Field("bing_verification_code")
    private String bingVerificationCode;

    @Field("google_verification_code")
    private String googleVerificationCode;

    @Field("yandex_verification_code")
    private String yandexVerificationCode;

    @Field("facebook_url")
    private String facebookURL;

    @Field("twitter_url")
    private String twitterURL;

    @Field("instagram_url")
    private String instagramURL;

    @Field("linked_in_url")
    private String linkedInURL;

    @Field("pinterest_url")
    private String pinterestURL;

    @Field("you_tube_url")
    private String youTubeURL;

    @Field("google_plus_url")
    private String googlePlusURL;

    @Field("github_url")
    private String githubURL;

    @Field("facebook_page_access_token")
    private String facebookPageAccessToken;

    @Field("ga_tracking_code")
    private String gaTrackingCode;

    @Field("github_client_id")
    private String githubClientId;

    @Field("github_client_secret")
    private String githubClientSecret;

    @Field("twitter_client_id")
    private String twitterClientId;

    @Field("twitter_client_secret")
    private String twitterClientSecret;

    @Field("facebook_client_id")
    private String facebookClientId;

    @Field("facebook_client_secret")
    private String facebookClientSecret;

    @Field("google_client_id")
    private String googleClientId;

    @Field("google_client_secret")
    private String googleClientSecret;

    @Field("linked_in_client_id")
    private String linkedInClientId;

    @Field("linked_in_client_secret")
    private String linkedInClientSecret;

    @Field("instagram_client_id")
    private String instagramClientId;

    @Field("instagram_client_secret")
    private String instagramClientSecret;

    @Field("mailchimp_api_key")
    private String mailchimpAPIKey;

    @Field("site_language")
    private String siteLanguage;

    @Field("time_zone")
    private String timeZone;

    @NotNull
    @Field("client_id")
    private String clientId;

    @NotNull
    @Field("slug")
    private String slug;

    @NotNull
    @Pattern(regexp = "'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,30}$'")
    @Field("email")
    private String email;

    @NotNull
    @Field("created_date")
    private ZonedDateTime createdDate;

    @DBRef
    @Field("degaUsers")
    @JsonIgnore
    private Set<DegaUser> degaUsers = new HashSet<>();

    @DBRef
    @Field("degaUserDefault")
    private Set<DegaUser> degaUserDefaults = new HashSet<>();
    @DBRef
    @Field("degaUserCurrent")
    private Set<DegaUser> degaUserCurrents = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Organization name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Organization phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public Organization siteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
        return this;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public String getTagLine() {
        return tagLine;
    }

    public Organization tagLine(String tagLine) {
        this.tagLine = tagLine;
        return this;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getDescription() {
        return description;
    }

    public Organization description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public Organization logoURL(String logoURL) {
        this.logoURL = logoURL;
        return this;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getLogoURLMobile() {
        return logoURLMobile;
    }

    public Organization logoURLMobile(String logoURLMobile) {
        this.logoURLMobile = logoURLMobile;
        return this;
    }

    public void setLogoURLMobile(String logoURLMobile) {
        this.logoURLMobile = logoURLMobile;
    }

    public String getFavIconURL() {
        return favIconURL;
    }

    public Organization favIconURL(String favIconURL) {
        this.favIconURL = favIconURL;
        return this;
    }

    public void setFavIconURL(String favIconURL) {
        this.favIconURL = favIconURL;
    }

    public String getMobileIconURL() {
        return mobileIconURL;
    }

    public Organization mobileIconURL(String mobileIconURL) {
        this.mobileIconURL = mobileIconURL;
        return this;
    }

    public void setMobileIconURL(String mobileIconURL) {
        this.mobileIconURL = mobileIconURL;
    }

    public String getBaiduVerificationCode() {
        return baiduVerificationCode;
    }

    public Organization baiduVerificationCode(String baiduVerificationCode) {
        this.baiduVerificationCode = baiduVerificationCode;
        return this;
    }

    public void setBaiduVerificationCode(String baiduVerificationCode) {
        this.baiduVerificationCode = baiduVerificationCode;
    }

    public String getBingVerificationCode() {
        return bingVerificationCode;
    }

    public Organization bingVerificationCode(String bingVerificationCode) {
        this.bingVerificationCode = bingVerificationCode;
        return this;
    }

    public void setBingVerificationCode(String bingVerificationCode) {
        this.bingVerificationCode = bingVerificationCode;
    }

    public String getGoogleVerificationCode() {
        return googleVerificationCode;
    }

    public Organization googleVerificationCode(String googleVerificationCode) {
        this.googleVerificationCode = googleVerificationCode;
        return this;
    }

    public void setGoogleVerificationCode(String googleVerificationCode) {
        this.googleVerificationCode = googleVerificationCode;
    }

    public String getYandexVerificationCode() {
        return yandexVerificationCode;
    }

    public Organization yandexVerificationCode(String yandexVerificationCode) {
        this.yandexVerificationCode = yandexVerificationCode;
        return this;
    }

    public void setYandexVerificationCode(String yandexVerificationCode) {
        this.yandexVerificationCode = yandexVerificationCode;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public Organization facebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
        return this;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }

    public String getTwitterURL() {
        return twitterURL;
    }

    public Organization twitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
        return this;
    }

    public void setTwitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public Organization instagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
        return this;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public String getLinkedInURL() {
        return linkedInURL;
    }

    public Organization linkedInURL(String linkedInURL) {
        this.linkedInURL = linkedInURL;
        return this;
    }

    public void setLinkedInURL(String linkedInURL) {
        this.linkedInURL = linkedInURL;
    }

    public String getPinterestURL() {
        return pinterestURL;
    }

    public Organization pinterestURL(String pinterestURL) {
        this.pinterestURL = pinterestURL;
        return this;
    }

    public void setPinterestURL(String pinterestURL) {
        this.pinterestURL = pinterestURL;
    }

    public String getYouTubeURL() {
        return youTubeURL;
    }

    public Organization youTubeURL(String youTubeURL) {
        this.youTubeURL = youTubeURL;
        return this;
    }

    public void setYouTubeURL(String youTubeURL) {
        this.youTubeURL = youTubeURL;
    }

    public String getGooglePlusURL() {
        return googlePlusURL;
    }

    public Organization googlePlusURL(String googlePlusURL) {
        this.googlePlusURL = googlePlusURL;
        return this;
    }

    public void setGooglePlusURL(String googlePlusURL) {
        this.googlePlusURL = googlePlusURL;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public Organization githubURL(String githubURL) {
        this.githubURL = githubURL;
        return this;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getFacebookPageAccessToken() {
        return facebookPageAccessToken;
    }

    public Organization facebookPageAccessToken(String facebookPageAccessToken) {
        this.facebookPageAccessToken = facebookPageAccessToken;
        return this;
    }

    public void setFacebookPageAccessToken(String facebookPageAccessToken) {
        this.facebookPageAccessToken = facebookPageAccessToken;
    }

    public String getGaTrackingCode() {
        return gaTrackingCode;
    }

    public Organization gaTrackingCode(String gaTrackingCode) {
        this.gaTrackingCode = gaTrackingCode;
        return this;
    }

    public void setGaTrackingCode(String gaTrackingCode) {
        this.gaTrackingCode = gaTrackingCode;
    }

    public String getGithubClientId() {
        return githubClientId;
    }

    public Organization githubClientId(String githubClientId) {
        this.githubClientId = githubClientId;
        return this;
    }

    public void setGithubClientId(String githubClientId) {
        this.githubClientId = githubClientId;
    }

    public String getGithubClientSecret() {
        return githubClientSecret;
    }

    public Organization githubClientSecret(String githubClientSecret) {
        this.githubClientSecret = githubClientSecret;
        return this;
    }

    public void setGithubClientSecret(String githubClientSecret) {
        this.githubClientSecret = githubClientSecret;
    }

    public String getTwitterClientId() {
        return twitterClientId;
    }

    public Organization twitterClientId(String twitterClientId) {
        this.twitterClientId = twitterClientId;
        return this;
    }

    public void setTwitterClientId(String twitterClientId) {
        this.twitterClientId = twitterClientId;
    }

    public String getTwitterClientSecret() {
        return twitterClientSecret;
    }

    public Organization twitterClientSecret(String twitterClientSecret) {
        this.twitterClientSecret = twitterClientSecret;
        return this;
    }

    public void setTwitterClientSecret(String twitterClientSecret) {
        this.twitterClientSecret = twitterClientSecret;
    }

    public String getFacebookClientId() {
        return facebookClientId;
    }

    public Organization facebookClientId(String facebookClientId) {
        this.facebookClientId = facebookClientId;
        return this;
    }

    public void setFacebookClientId(String facebookClientId) {
        this.facebookClientId = facebookClientId;
    }

    public String getFacebookClientSecret() {
        return facebookClientSecret;
    }

    public Organization facebookClientSecret(String facebookClientSecret) {
        this.facebookClientSecret = facebookClientSecret;
        return this;
    }

    public void setFacebookClientSecret(String facebookClientSecret) {
        this.facebookClientSecret = facebookClientSecret;
    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public Organization googleClientId(String googleClientId) {
        this.googleClientId = googleClientId;
        return this;
    }

    public void setGoogleClientId(String googleClientId) {
        this.googleClientId = googleClientId;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }

    public Organization googleClientSecret(String googleClientSecret) {
        this.googleClientSecret = googleClientSecret;
        return this;
    }

    public void setGoogleClientSecret(String googleClientSecret) {
        this.googleClientSecret = googleClientSecret;
    }

    public String getLinkedInClientId() {
        return linkedInClientId;
    }

    public Organization linkedInClientId(String linkedInClientId) {
        this.linkedInClientId = linkedInClientId;
        return this;
    }

    public void setLinkedInClientId(String linkedInClientId) {
        this.linkedInClientId = linkedInClientId;
    }

    public String getLinkedInClientSecret() {
        return linkedInClientSecret;
    }

    public Organization linkedInClientSecret(String linkedInClientSecret) {
        this.linkedInClientSecret = linkedInClientSecret;
        return this;
    }

    public void setLinkedInClientSecret(String linkedInClientSecret) {
        this.linkedInClientSecret = linkedInClientSecret;
    }

    public String getInstagramClientId() {
        return instagramClientId;
    }

    public Organization instagramClientId(String instagramClientId) {
        this.instagramClientId = instagramClientId;
        return this;
    }

    public void setInstagramClientId(String instagramClientId) {
        this.instagramClientId = instagramClientId;
    }

    public String getInstagramClientSecret() {
        return instagramClientSecret;
    }

    public Organization instagramClientSecret(String instagramClientSecret) {
        this.instagramClientSecret = instagramClientSecret;
        return this;
    }

    public void setInstagramClientSecret(String instagramClientSecret) {
        this.instagramClientSecret = instagramClientSecret;
    }

    public String getMailchimpAPIKey() {
        return mailchimpAPIKey;
    }

    public Organization mailchimpAPIKey(String mailchimpAPIKey) {
        this.mailchimpAPIKey = mailchimpAPIKey;
        return this;
    }

    public void setMailchimpAPIKey(String mailchimpAPIKey) {
        this.mailchimpAPIKey = mailchimpAPIKey;
    }

    public String getSiteLanguage() {
        return siteLanguage;
    }

    public Organization siteLanguage(String siteLanguage) {
        this.siteLanguage = siteLanguage;
        return this;
    }

    public void setSiteLanguage(String siteLanguage) {
        this.siteLanguage = siteLanguage;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Organization timeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getClientId() {
        return clientId;
    }

    public Organization clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSlug() {
        return slug;
    }

    public Organization slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getEmail() {
        return email;
    }

    public Organization email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Organization createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<DegaUser> getDegaUsers() {
        return degaUsers;
    }

    public Organization degaUsers(Set<DegaUser> degaUsers) {
        this.degaUsers = degaUsers;
        return this;
    }

    public Organization addDegaUser(DegaUser degaUser) {
        this.degaUsers.add(degaUser);
        degaUser.getOrganizations().add(this);
        return this;
    }

    public Organization removeDegaUser(DegaUser degaUser) {
        this.degaUsers.remove(degaUser);
        degaUser.getOrganizations().remove(this);
        return this;
    }

    public void setDegaUsers(Set<DegaUser> degaUsers) {
        this.degaUsers = degaUsers;
    }

    public Set<DegaUser> getDegaUserDefaults() {
        return degaUserDefaults;
    }

    public Organization degaUserDefaults(Set<DegaUser> degaUsers) {
        this.degaUserDefaults = degaUsers;
        return this;
    }

    public Organization addDegaUserDefault(DegaUser degaUser) {
        this.degaUserDefaults.add(degaUser);
        degaUser.setOrganizationDefault(this);
        return this;
    }

    public Organization removeDegaUserDefault(DegaUser degaUser) {
        this.degaUserDefaults.remove(degaUser);
        degaUser.setOrganizationDefault(null);
        return this;
    }

    public void setDegaUserDefaults(Set<DegaUser> degaUsers) {
        this.degaUserDefaults = degaUsers;
    }

    public Set<DegaUser> getDegaUserCurrents() {
        return degaUserCurrents;
    }

    public Organization degaUserCurrents(Set<DegaUser> degaUsers) {
        this.degaUserCurrents = degaUsers;
        return this;
    }

    public Organization addDegaUserCurrent(DegaUser degaUser) {
        this.degaUserCurrents.add(degaUser);
        degaUser.setOrganizationCurrent(this);
        return this;
    }

    public Organization removeDegaUserCurrent(DegaUser degaUser) {
        this.degaUserCurrents.remove(degaUser);
        degaUser.setOrganizationCurrent(null);
        return this;
    }

    public void setDegaUserCurrents(Set<DegaUser> degaUsers) {
        this.degaUserCurrents = degaUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Organization organization = (Organization) o;
        if (organization.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", siteTitle='" + getSiteTitle() + "'" +
            ", tagLine='" + getTagLine() + "'" +
            ", description='" + getDescription() + "'" +
            ", logoURL='" + getLogoURL() + "'" +
            ", logoURLMobile='" + getLogoURLMobile() + "'" +
            ", favIconURL='" + getFavIconURL() + "'" +
            ", mobileIconURL='" + getMobileIconURL() + "'" +
            ", baiduVerificationCode='" + getBaiduVerificationCode() + "'" +
            ", bingVerificationCode='" + getBingVerificationCode() + "'" +
            ", googleVerificationCode='" + getGoogleVerificationCode() + "'" +
            ", yandexVerificationCode='" + getYandexVerificationCode() + "'" +
            ", facebookURL='" + getFacebookURL() + "'" +
            ", twitterURL='" + getTwitterURL() + "'" +
            ", instagramURL='" + getInstagramURL() + "'" +
            ", linkedInURL='" + getLinkedInURL() + "'" +
            ", pinterestURL='" + getPinterestURL() + "'" +
            ", youTubeURL='" + getYouTubeURL() + "'" +
            ", googlePlusURL='" + getGooglePlusURL() + "'" +
            ", githubURL='" + getGithubURL() + "'" +
            ", facebookPageAccessToken='" + getFacebookPageAccessToken() + "'" +
            ", gaTrackingCode='" + getGaTrackingCode() + "'" +
            ", githubClientId='" + getGithubClientId() + "'" +
            ", githubClientSecret='" + getGithubClientSecret() + "'" +
            ", twitterClientId='" + getTwitterClientId() + "'" +
            ", twitterClientSecret='" + getTwitterClientSecret() + "'" +
            ", facebookClientId='" + getFacebookClientId() + "'" +
            ", facebookClientSecret='" + getFacebookClientSecret() + "'" +
            ", googleClientId='" + getGoogleClientId() + "'" +
            ", googleClientSecret='" + getGoogleClientSecret() + "'" +
            ", linkedInClientId='" + getLinkedInClientId() + "'" +
            ", linkedInClientSecret='" + getLinkedInClientSecret() + "'" +
            ", instagramClientId='" + getInstagramClientId() + "'" +
            ", instagramClientSecret='" + getInstagramClientSecret() + "'" +
            ", mailchimpAPIKey='" + getMailchimpAPIKey() + "'" +
            ", siteLanguage='" + getSiteLanguage() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", slug='" + getSlug() + "'" +
            ", email='" + getEmail() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
