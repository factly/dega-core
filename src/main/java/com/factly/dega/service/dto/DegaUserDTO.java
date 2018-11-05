package com.factly.dega.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DegaUser entity.
 */
public class DegaUserDTO implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    @NotNull
    private String displayName;

    @NotNull
    private String email;

    private String website;

    private String facebookURL;

    private String twitterURL;

    private String instagramURL;

    private String linkedinURL;

    private String githubURL;

    private String profilePicture;

    private String description;

    private Boolean isActive;

    @NotNull
    private String slug;

    private String roleId;

    private String roleName;

    private Set<OrganizationDTO> organizations = new HashSet<>();

    private String organizationDefaultId;

    private String organizationDefaultName;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", facebookURL='" + getFacebookURL() + "'" +
            ", twitterURL='" + getTwitterURL() + "'" +
            ", instagramURL='" + getInstagramURL() + "'" +
            ", linkedinURL='" + getLinkedinURL() + "'" +
            ", githubURL='" + getGithubURL() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", slug='" + getSlug() + "'" +
            ", role=" + getRoleId() +
            ", role='" + getRoleName() + "'" +
            ", organizationDefault=" + getOrganizationDefaultId() +
            ", organizationDefault='" + getOrganizationDefaultName() + "'" +
            "}";
    }
}
