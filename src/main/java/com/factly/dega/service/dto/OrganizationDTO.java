package com.factly.dega.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Organization entity.
 */
public class OrganizationDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private String phone;

    @NotNull
    private String siteTitle;

    private String tagLine;

    private String description;

    private String logoURL;

    private String logoURLMobile;

    private String favIconURL;

    private String mobileIconURL;

    private String baiduVerificationCode;

    private String bingVerificationCode;

    private String googleVerificationCode;

    private String yandexVerificationCode;

    private String facebookURL;

    private String twitterURL;

    private String instagramURL;

    private String linkedInURL;

    private String pinterestURL;

    private String youTubeURL;

    private String googlePlusURL;

    private String githubURL;

    private String facebookPageAccessToken;

    private String gaTrackingCode;

    private String githubClientId;

    private String githubClientSecret;

    private String twitterClientId;

    private String twitterClientSecret;

    private String facebookClientId;

    private String facebookClientSecret;

    private String googleClientId;

    private String googleClientSecret;

    private String linkedInClientId;

    private String linkedInClientSecret;

    private String instagramClientId;

    private String instagramClientSecret;

    private String mailchimpAPIKey;

    private String siteLanguage;

    private String timeZone;

    @NotNull
    private String clientId;

    private String slug;

    @NotNull
    /*@Pattern(regexp = "'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,30}$'")*/
    private String email;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastUpdatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getLogoURLMobile() {
        return logoURLMobile;
    }

    public void setLogoURLMobile(String logoURLMobile) {
        this.logoURLMobile = logoURLMobile;
    }

    public String getFavIconURL() {
        return favIconURL;
    }

    public void setFavIconURL(String favIconURL) {
        this.favIconURL = favIconURL;
    }

    public String getMobileIconURL() {
        return mobileIconURL;
    }

    public void setMobileIconURL(String mobileIconURL) {
        this.mobileIconURL = mobileIconURL;
    }

    public String getBaiduVerificationCode() {
        return baiduVerificationCode;
    }

    public void setBaiduVerificationCode(String baiduVerificationCode) {
        this.baiduVerificationCode = baiduVerificationCode;
    }

    public String getBingVerificationCode() {
        return bingVerificationCode;
    }

    public void setBingVerificationCode(String bingVerificationCode) {
        this.bingVerificationCode = bingVerificationCode;
    }

    public String getGoogleVerificationCode() {
        return googleVerificationCode;
    }

    public void setGoogleVerificationCode(String googleVerificationCode) {
        this.googleVerificationCode = googleVerificationCode;
    }

    public String getYandexVerificationCode() {
        return yandexVerificationCode;
    }

    public void setYandexVerificationCode(String yandexVerificationCode) {
        this.yandexVerificationCode = yandexVerificationCode;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }

    public String getTwitterURL() {
        return twitterURL;
    }

    public void setTwitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public String getLinkedInURL() {
        return linkedInURL;
    }

    public void setLinkedInURL(String linkedInURL) {
        this.linkedInURL = linkedInURL;
    }

    public String getPinterestURL() {
        return pinterestURL;
    }

    public void setPinterestURL(String pinterestURL) {
        this.pinterestURL = pinterestURL;
    }

    public String getYouTubeURL() {
        return youTubeURL;
    }

    public void setYouTubeURL(String youTubeURL) {
        this.youTubeURL = youTubeURL;
    }

    public String getGooglePlusURL() {
        return googlePlusURL;
    }

    public void setGooglePlusURL(String googlePlusURL) {
        this.googlePlusURL = googlePlusURL;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getFacebookPageAccessToken() {
        return facebookPageAccessToken;
    }

    public void setFacebookPageAccessToken(String facebookPageAccessToken) {
        this.facebookPageAccessToken = facebookPageAccessToken;
    }

    public String getGaTrackingCode() {
        return gaTrackingCode;
    }

    public void setGaTrackingCode(String gaTrackingCode) {
        this.gaTrackingCode = gaTrackingCode;
    }

    public String getGithubClientId() {
        return githubClientId;
    }

    public void setGithubClientId(String githubClientId) {
        this.githubClientId = githubClientId;
    }

    public String getGithubClientSecret() {
        return githubClientSecret;
    }

    public void setGithubClientSecret(String githubClientSecret) {
        this.githubClientSecret = githubClientSecret;
    }

    public String getTwitterClientId() {
        return twitterClientId;
    }

    public void setTwitterClientId(String twitterClientId) {
        this.twitterClientId = twitterClientId;
    }

    public String getTwitterClientSecret() {
        return twitterClientSecret;
    }

    public void setTwitterClientSecret(String twitterClientSecret) {
        this.twitterClientSecret = twitterClientSecret;
    }

    public String getFacebookClientId() {
        return facebookClientId;
    }

    public void setFacebookClientId(String facebookClientId) {
        this.facebookClientId = facebookClientId;
    }

    public String getFacebookClientSecret() {
        return facebookClientSecret;
    }

    public void setFacebookClientSecret(String facebookClientSecret) {
        this.facebookClientSecret = facebookClientSecret;
    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public void setGoogleClientId(String googleClientId) {
        this.googleClientId = googleClientId;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }

    public void setGoogleClientSecret(String googleClientSecret) {
        this.googleClientSecret = googleClientSecret;
    }

    public String getLinkedInClientId() {
        return linkedInClientId;
    }

    public void setLinkedInClientId(String linkedInClientId) {
        this.linkedInClientId = linkedInClientId;
    }

    public String getLinkedInClientSecret() {
        return linkedInClientSecret;
    }

    public void setLinkedInClientSecret(String linkedInClientSecret) {
        this.linkedInClientSecret = linkedInClientSecret;
    }

    public String getInstagramClientId() {
        return instagramClientId;
    }

    public void setInstagramClientId(String instagramClientId) {
        this.instagramClientId = instagramClientId;
    }

    public String getInstagramClientSecret() {
        return instagramClientSecret;
    }

    public void setInstagramClientSecret(String instagramClientSecret) {
        this.instagramClientSecret = instagramClientSecret;
    }

    public String getMailchimpAPIKey() {
        return mailchimpAPIKey;
    }

    public void setMailchimpAPIKey(String mailchimpAPIKey) {
        this.mailchimpAPIKey = mailchimpAPIKey;
    }

    public String getSiteLanguage() {
        return siteLanguage;
    }

    public void setSiteLanguage(String siteLanguage) {
        this.siteLanguage = siteLanguage;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganizationDTO organizationDTO = (OrganizationDTO) o;
        if (organizationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organizationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganizationDTO{" +
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
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
