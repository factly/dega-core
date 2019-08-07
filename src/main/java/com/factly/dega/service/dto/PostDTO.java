package com.factly.dega.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Post entity.
 */
public class PostDTO implements Serializable {

    private String id;

    @NotNull
    private String title;

    private String clientId;

    @NotNull
    private String content;

    private String excerpt;

    private ZonedDateTime publishedDate;

    private ZonedDateTime lastUpdatedDate;

    private Boolean featured;

    private Boolean sticky;

    private String updates;

    private String slug;

    private String password;

    private String subTitle;

    private ZonedDateTime createdDate;

    private Set<TagDTO> tags = new HashSet<>();

    private Set<CategoryDTO> categories = new HashSet<>();

    private String statusId;

    private String statusName;

    private String formatId;

    private String formatName;

    private Set<DegaUserDTO> degaUsers = new HashSet<>();

    private MediaDTO mediaDTO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public ZonedDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(ZonedDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Boolean isFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean isSticky() {
        return sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    public String getUpdates() {
        return updates;
    }

    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public Set<DegaUserDTO> getDegaUsers() {
        return degaUsers;
    }

    public void setDegaUsers(Set<DegaUserDTO> degaUsers) {
        this.degaUsers = degaUsers;
    }

    public MediaDTO getMediaDTO() {
        return mediaDTO;
    }

    public void setMediaDTO(MediaDTO mediaDTO) {
        this.mediaDTO = mediaDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (postDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", content='" + getContent() + "'" +
            ", excerpt='" + getExcerpt() + "'" +
            ", publishedDate='" + getPublishedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", featured='" + isFeatured() + "'" +
            ", sticky='" + isSticky() + "'" +
            ", updates='" + getUpdates() + "'" +
            ", slug='" + getSlug() + "'" +
            ", password='" + getPassword() + "'" +
            ", subTitle='" + getSubTitle() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", status=" + getStatusId() +
            ", status='" + getStatusName() + "'" +
            ", format=" + getFormatId() +
            ", format='" + getFormatName() + "'" +
            "}";
    }
}
