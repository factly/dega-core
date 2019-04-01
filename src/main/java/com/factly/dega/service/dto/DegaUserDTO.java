package com.factly.dega.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the DegaUser entity.
 */
public class DegaUserDTO implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    @NotNull
    private String displayName;

    private String website;

    private String facebookURL;

    private String twitterURL;

    private String instagramURL;

    private String linkedinURL;

    private String githubURL;

    private String profilePicture;

    private String description;

    private String slug;

    @NotNull
    private Boolean enabled;

    private Boolean emailVerified;

    @NotNull
    private String email;

    private ZonedDateTime createdDate;

    private String roleId;

    private String roleName;

    private Set<OrganizationDTO> organizations = new HashSet<>();

    private String organizationDefaultId;

    private String organizationDefaultName;

    private String organizationCurrentId;

    private String organizationCurrentName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getLinkedinURL() {
        return linkedinURL;
    }

    public void setLinkedinURL(String linkedinURL) {
        this.linkedinURL = linkedinURL;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<OrganizationDTO> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrganizationDTO> organizations) {
        this.organizations = organizations;
    }

    public String getOrganizationDefaultId() {
        return organizationDefaultId;
    }

    public void setOrganizationDefaultId(String organizationId) {
        this.organizationDefaultId = organizationId;
    }

    public String getOrganizationDefaultName() {
        return organizationDefaultName;
    }

    public void setOrganizationDefaultName(String organizationName) {
        this.organizationDefaultName = organizationName;
    }

    public String getOrganizationCurrentId() {
        return organizationCurrentId;
    }

    public void setOrganizationCurrentId(String organizationId) {
        this.organizationCurrentId = organizationId;
    }

    public String getOrganizationCurrentName() {
        return organizationCurrentName;
    }

    public void setOrganizationCurrentName(String organizationName) {
        this.organizationCurrentName = organizationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DegaUserDTO degaUserDTO = (DegaUserDTO) o;
        if (degaUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), degaUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DegaUserDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", website='" + getWebsite() + "'" +
            ", facebookURL='" + getFacebookURL() + "'" +
            ", twitterURL='" + getTwitterURL() + "'" +
            ", instagramURL='" + getInstagramURL() + "'" +
            ", linkedinURL='" + getLinkedinURL() + "'" +
            ", githubURL='" + getGithubURL() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            ", description='" + getDescription() + "'" +
            ", slug='" + getSlug() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", emailVerified='" + isEmailVerified() + "'" +
            ", email='" + getEmail() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", role=" + getRoleId() +
            ", role='" + getRoleName() + "'" +
            ", organizationDefault=" + getOrganizationDefaultId() +
            ", organizationDefault='" + getOrganizationDefaultName() + "'" +
            ", organizationCurrent=" + getOrganizationCurrentId() +
            ", organizationCurrent='" + getOrganizationCurrentName() + "'" +
            "}";
    }
}
