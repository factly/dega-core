package com.factly.dega.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DegaUser.
 */
@Document(collection = "dega_user")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "degauser")
public class DegaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @NotNull
    @Field("display_name")
    private String displayName;

    @NotNull
    @Field("email")
    private String email;

    @Field("website")
    private String website;

    @Field("facebook_url")
    private String facebookURL;

    @Field("twitter_url")
    private String twitterURL;

    @Field("instagram_url")
    private String instagramURL;

    @Field("linkedin_url")
    private String linkedinURL;

    @Field("github_url")
    private String githubURL;

    @Field("profile_picture")
    private String profilePicture;

    @Field("description")
    private String description;

    @NotNull
    @Field("client_id")
    private String clientId;

    @Field("is_active")
    private Boolean isActive;

    @NotNull
    @Field("slug")
    private String slug;

    @DBRef
    @Field("role")
    @JsonIgnoreProperties("")
    private Role role;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public DegaUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public DegaUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public DegaUser displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public DegaUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public DegaUser website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public DegaUser facebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
        return this;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }

    public String getTwitterURL() {
        return twitterURL;
    }

    public DegaUser twitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
        return this;
    }

    public void setTwitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public DegaUser instagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
        return this;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public String getLinkedinURL() {
        return linkedinURL;
    }

    public DegaUser linkedinURL(String linkedinURL) {
        this.linkedinURL = linkedinURL;
        return this;
    }

    public void setLinkedinURL(String linkedinURL) {
        this.linkedinURL = linkedinURL;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public DegaUser githubURL(String githubURL) {
        this.githubURL = githubURL;
        return this;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public DegaUser profilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDescription() {
        return description;
    }

    public DegaUser description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientId() {
        return clientId;
    }

    public DegaUser clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public DegaUser isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSlug() {
        return slug;
    }

    public DegaUser slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Role getRole() {
        return role;
    }

    public DegaUser role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
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
        DegaUser degaUser = (DegaUser) o;
        if (degaUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), degaUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DegaUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", facebookURL='" + getFacebookURL() + "'" +
            ", twitterURL='" + getTwitterURL() + "'" +
            ", instagramURL='" + getInstagramURL() + "'" +
            ", linkedinURL='" + getLinkedinURL() + "'" +
            ", githubURL='" + getGithubURL() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            ", description='" + getDescription() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", slug='" + getSlug() + "'" +
            "}";
    }
}
