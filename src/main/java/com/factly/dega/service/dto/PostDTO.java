package com.factly.dega.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Post entity.
 */
public class PostDTO implements Serializable {

    private String id;

    @NotNull
    private String title;

    @NotNull
    private String clientId;

    @NotNull
    private String content;

    private String excerpt;

    @NotNull
    private ZonedDateTime publishedDate;

    @NotNull
    private ZonedDateTime publishedDateGMT;

    @NotNull
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private ZonedDateTime lastUpdatedDateGMT;

    private Boolean featured;

    private Boolean sticky;

    private String updates;

    @NotNull
    private String slug;

    private String featuredImage;

    private String statusId;

    private String statusName;

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

    public ZonedDateTime getPublishedDateGMT() {
        return publishedDateGMT;
    }

    public void setPublishedDateGMT(ZonedDateTime publishedDateGMT) {
        this.publishedDateGMT = publishedDateGMT;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public ZonedDateTime getLastUpdatedDateGMT() {
        return lastUpdatedDateGMT;
    }

    public void setLastUpdatedDateGMT(ZonedDateTime lastUpdatedDateGMT) {
        this.lastUpdatedDateGMT = lastUpdatedDateGMT;
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

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
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
            ", publishedDateGMT='" + getPublishedDateGMT() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lastUpdatedDateGMT='" + getLastUpdatedDateGMT() + "'" +
            ", featured='" + isFeatured() + "'" +
            ", sticky='" + isSticky() + "'" +
            ", updates='" + getUpdates() + "'" +
            ", slug='" + getSlug() + "'" +
            ", featuredImage='" + getFeaturedImage() + "'" +
            ", status=" + getStatusId() +
            ", status='" + getStatusName() + "'" +
            "}";
    }
}
