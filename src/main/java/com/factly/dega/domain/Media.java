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
 * A Media.
 */
@Document(collection = "media")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "media")
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("url")
    private String url;

    @Field("file_size")
    private String fileSize;

    @Field("dimensions")
    private String dimensions;

    @Field("title")
    private String title;

    @Field("caption")
    private String caption;

    @Field("alt_text")
    private String altText;

    @Field("description")
    private String description;

    @NotNull
    @Field("uploaded_by")
    private String uploadedBy;

    @NotNull
    @Field("published_date")
    private ZonedDateTime publishedDate;

    @NotNull
    @Field("last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Field("slug")
    private String slug;

    @NotNull
    @Field("client_id")
    private String clientId;

    @NotNull
    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("relative_url")
    private String relativeURL;

    @Field("source_url")
    private String sourceURL;

    @DBRef
    @Field("post")
    private Set<Post> posts = new HashSet<>();
    @DBRef
    @Field("degaUser")
    private Set<DegaUser> degaUsers = new HashSet<>();
    @DBRef
    @Field("organizationLogo")
    private Set<Organization> organizationLogos = new HashSet<>();
    @DBRef
    @Field("organizationMobileLogo")
    private Set<Organization> organizationMobileLogos = new HashSet<>();
    @DBRef
    @Field("organizationFavicon")
    private Set<Organization> organizationFavicons = new HashSet<>();
    @DBRef
    @Field("organizationMobileIcon")
    private Set<Organization> organizationMobileIcons = new HashSet<>();
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

    public Media name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Media type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public Media url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileSize() {
        return fileSize;
    }

    public Media fileSize(String fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDimensions() {
        return dimensions;
    }

    public Media dimensions(String dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getTitle() {
        return title;
    }

    public Media title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public Media caption(String caption) {
        this.caption = caption;
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getAltText() {
        return altText;
    }

    public Media altText(String altText) {
        this.altText = altText;
        return this;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getDescription() {
        return description;
    }

    public Media description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public Media uploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
        return this;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public ZonedDateTime getPublishedDate() {
        return publishedDate;
    }

    public Media publishedDate(ZonedDateTime publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    public void setPublishedDate(ZonedDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Media lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getSlug() {
        return slug;
    }

    public Media slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getClientId() {
        return clientId;
    }

    public Media clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Media createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getRelativeURL() {
        return relativeURL;
    }

    public Media relativeURL(String relativeURL) {
        this.relativeURL = relativeURL;
        return this;
    }

    public void setRelativeURL(String relativeURL) {
        this.relativeURL = relativeURL;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public Media sourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
        return this;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

//    public Set<Post> getPosts() {
//        return posts;
//    }
//
//    public Media posts(Set<Post> posts) {
//        this.posts = posts;
//        return this;
//    }
//
//    public Media addPost(Post post) {
//        this.posts.add(post);
//        post.setMedia(this);
//        return this;
//    }
//
//    public Media removePost(Post post) {
//        this.posts.remove(post);
//        post.setMedia(null);
//        return this;
//    }
//
//    public void setPosts(Set<Post> posts) {
//        this.posts = posts;
//    }

    public Set<DegaUser> getDegaUsers() {
        return degaUsers;
    }

    public Media degaUsers(Set<DegaUser> degaUsers) {
        this.degaUsers = degaUsers;
        return this;
    }

    public Media addDegaUser(DegaUser degaUser) {
        this.degaUsers.add(degaUser);
        degaUser.setMedia(this);
        return this;
    }

    public Media removeDegaUser(DegaUser degaUser) {
        this.degaUsers.remove(degaUser);
        degaUser.setMedia(null);
        return this;
    }

    public void setDegaUsers(Set<DegaUser> degaUsers) {
        this.degaUsers = degaUsers;
    }

//    public Set<Organization> getOrganizationLogos() {
//        return organizationLogos;
//    }

//    public Media organizationLogos(Set<Organization> organizations) {
//        this.organizationLogos = organizations;
//        return this;
//    }
//
//    public Media addOrganizationLogo(Organization organization) {
//        this.organizationLogos.add(organization);
//        organization.setMediaLogo(this);
//        return this;
//    }
//
//    public Media removeOrganizationLogo(Organization organization) {
//        this.organizationLogos.remove(organization);
//        organization.setMediaLogo(null);
//        return this;
//    }
//
//    public void setOrganizationLogos(Set<Organization> organizations) {
//        this.organizationLogos = organizations;
//    }
//
//    public Set<Organization> getOrganizationMobileLogos() {
//        return organizationMobileLogos;
//    }
//
//    public Media organizationMobileLogos(Set<Organization> organizations) {
//        this.organizationMobileLogos = organizations;
//        return this;
//    }
//
//    public Media addOrganizationMobileLogo(Organization organization) {
//        this.organizationMobileLogos.add(organization);
//        organization.setMediaMobileLogo(this);
//        return this;
//    }
//
//    public Media removeOrganizationMobileLogo(Organization organization) {
//        this.organizationMobileLogos.remove(organization);
//        organization.setMediaMobileLogo(null);
//        return this;
//    }
//
//    public void setOrganizationMobileLogos(Set<Organization> organizations) {
//        this.organizationMobileLogos = organizations;
//    }
//
//    public Set<Organization> getOrganizationFavicons() {
//        return organizationFavicons;
//    }
//
//    public Media organizationFavicons(Set<Organization> organizations) {
//        this.organizationFavicons = organizations;
//        return this;
//    }
//
//    public Media addOrganizationFavicon(Organization organization) {
//        this.organizationFavicons.add(organization);
//        organization.setMediaFavicon(this);
//        return this;
//    }
//
//    public Media removeOrganizationFavicon(Organization organization) {
//        this.organizationFavicons.remove(organization);
//        organization.setMediaFavicon(null);
//        return this;
//    }
//
//    public void setOrganizationFavicons(Set<Organization> organizations) {
//        this.organizationFavicons = organizations;
//    }
//
//    public Set<Organization> getOrganizationMobileIcons() {
//        return organizationMobileIcons;
//    }
//
//    public Media organizationMobileIcons(Set<Organization> organizations) {
//        this.organizationMobileIcons = organizations;
//        return this;
//    }
//
//    public Media addOrganizationMobileIcon(Organization organization) {
//        this.organizationMobileIcons.add(organization);
//        organization.setMediaMobileIcon(this);
//        return this;
//    }
//
//    public Media removeOrganizationMobileIcon(Organization organization) {
//        this.organizationMobileIcons.remove(organization);
//        organization.setMediaMobileIcon(null);
//        return this;
//    }
//
//    public void setOrganizationMobileIcons(Set<Organization> organizations) {
//        this.organizationMobileIcons = organizations;
//    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Media media = (Media) o;
        if (media.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), media.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Media{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", url='" + getUrl() + "'" +
            ", fileSize='" + getFileSize() + "'" +
            ", dimensions='" + getDimensions() + "'" +
            ", title='" + getTitle() + "'" +
            ", caption='" + getCaption() + "'" +
            ", altText='" + getAltText() + "'" +
            ", description='" + getDescription() + "'" +
            ", uploadedBy='" + getUploadedBy() + "'" +
            ", publishedDate='" + getPublishedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", slug='" + getSlug() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", relativeURL='" + getRelativeURL() + "'" +
            ", sourceURL='" + getSourceURL() + "'" +
            "}";
    }
}
