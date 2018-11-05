package com.factly.dega.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Media entity.
 */
public class MediaDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private String url;

    private String fileSize;

    private String dimensions;

    private String title;

    private String caption;

    private String altText;

    private String description;

    @NotNull
    private String uploadedBy;

    @NotNull
    private ZonedDateTime publishedDate;

    @NotNull
    private ZonedDateTime publishedDateGMT;

    @NotNull
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private ZonedDateTime lastUpdatedDateGMT;

    @NotNull
    private String slug;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MediaDTO mediaDTO = (MediaDTO) o;
        if (mediaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaDTO{" +
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
            ", publishedDateGMT='" + getPublishedDateGMT() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lastUpdatedDateGMT='" + getLastUpdatedDateGMT() + "'" +
            ", slug='" + getSlug() + "'" +
            "}";
    }
}
