package com.factly.dega.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Field("slug")
    private String slug;

    @NotNull
    @Field("enabled")
    private Boolean enabled;

    @Field("email_verified")
    private Boolean emailVerified;

    @NotNull
    @Field("email")
    private String email;

    @NotNull
    @Field("created_date")
    private ZonedDateTime createdDate;

    @DBRef
    @Field("role")
    @JsonIgnoreProperties("degaUsers")
    private Role role;

    @DBRef
    @Field("organizations")
    private Set<Organization> organizations = new HashSet<>();

    @DBRef
    @Field("organizationDefault")
    @JsonIgnoreProperties("degaUserDefaults")
    private Organization organizationDefault;

    @DBRef
    @Field("organizationCurrent")
    @JsonIgnoreProperties("degaUserCurrents")
    private Organization organizationCurrent;

    @DBRef
    @Field("posts")
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();

    @DBRef
    @Field("roleMapping")
    private Set<RoleMapping> roleMappings = new HashSet<>();
    @DBRef
    @Field("roleMappingDegaUsers")
    private Set<RoleMapping> roleMappingDegaUsers = new HashSet<>();

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

    public Boolean isEnabled() {
        return enabled;
    }

    public DegaUser enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isEmailVerified() {
        return emailVerified;
    }

    public DegaUser emailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
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

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public DegaUser createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
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

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public DegaUser organizations(Set<Organization> organizations) {
        this.organizations = organizations;
        return this;
    }

    public DegaUser addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.getDegaUsers().add(this);
        return this;
    }

    public DegaUser removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.getDegaUsers().remove(this);
        return this;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public Organization getOrganizationDefault() {
        return organizationDefault;
    }

    public DegaUser organizationDefault(Organization organization) {
        this.organizationDefault = organization;
        return this;
    }

    public void setOrganizationDefault(Organization organization) {
        this.organizationDefault = organization;
    }

    public Organization getOrganizationCurrent() {
        return organizationCurrent;
    }

    public DegaUser organizationCurrent(Organization organization) {
        this.organizationCurrent = organization;
        return this;
    }

    public void setOrganizationCurrent(Organization organization) {
        this.organizationCurrent = organization;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public DegaUser posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public DegaUser addPost(Post post) {
        this.posts.add(post);
        post.getDegaUsers().add(this);
        return this;
    }

    public DegaUser removePost(Post post) {
        this.posts.remove(post);
        post.getDegaUsers().remove(this);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<RoleMapping> getRoleMappings() {
        return roleMappings;
    }

    public DegaUser roleMappings(Set<RoleMapping> roleMappings) {
        this.roleMappings = roleMappings;
        return this;
    }

    public DegaUser addRoleMapping(RoleMapping roleMapping) {
        this.roleMappings.add(roleMapping);
        roleMapping.setDegaUser(this);
        return this;
    }

    public DegaUser removeRoleMapping(RoleMapping roleMapping) {
        this.roleMappings.remove(roleMapping);
        roleMapping.setDegaUser(null);
        return this;
    }

    public void setRoleMappings(Set<RoleMapping> roleMappings) {
        this.roleMappings = roleMappings;
    }

    public Set<RoleMapping> getRoleMappingDegaUsers() {
        return roleMappingDegaUsers;
    }

    public DegaUser roleMappingDegaUsers(Set<RoleMapping> roleMappings) {
        this.roleMappingDegaUsers = roleMappings;
        return this;
    }

    public DegaUser addRoleMappingDegaUser(RoleMapping roleMapping) {
        this.roleMappingDegaUsers.add(roleMapping);
        roleMapping.getDegaUserRoleMappings().add(this);
        return this;
    }

    public DegaUser removeRoleMappingDegaUser(RoleMapping roleMapping) {
        this.roleMappingDegaUsers.remove(roleMapping);
        roleMapping.getDegaUserRoleMappings().remove(this);
        return this;
    }

    public void setRoleMappingDegaUsers(Set<RoleMapping> roleMappings) {
        this.roleMappingDegaUsers = roleMappings;
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
            "}";
    }
}
