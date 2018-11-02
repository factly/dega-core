package com.factly.dega.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Post.
 */
@Document(collection = "post")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("client_id")
    private String clientId;

    @NotNull
    @Field("content")
    private String content;

    @Field("excerpt")
    private String excerpt;

    @NotNull
    @Field("published_date")
    private ZonedDateTime publishedDate;

    @NotNull
    @Field("published_date_gmt")
    private ZonedDateTime publishedDateGMT;

    @NotNull
    @Field("last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Field("last_updated_date_gmt")
    private ZonedDateTime lastUpdatedDateGMT;

    @Field("featured")
    private Boolean featured;

    @Field("sticky")
    private Boolean sticky;

    @Field("updates")
    private String updates;

    @NotNull
    @Field("slug")
    private String slug;

    @Field("featured_image")
    private String featuredImage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Post title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClientId() {
        return clientId;
    }

    public Post clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getContent() {
        return content;
    }

    public Post content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public Post excerpt(String excerpt) {
        this.excerpt = excerpt;
        return this;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public ZonedDateTime getPublishedDate() {
        return publishedDate;
    }

    public Post publishedDate(ZonedDateTime publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    public void setPublishedDate(ZonedDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ZonedDateTime getPublishedDateGMT() {
        return publishedDateGMT;
    }

    public Post publishedDateGMT(ZonedDateTime publishedDateGMT) {
        this.publishedDateGMT = publishedDateGMT;
        return this;
    }

    public void setPublishedDateGMT(ZonedDateTime publishedDateGMT) {
        this.publishedDateGMT = publishedDateGMT;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Post lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public ZonedDateTime getLastUpdatedDateGMT() {
        return lastUpdatedDateGMT;
    }

    public Post lastUpdatedDateGMT(ZonedDateTime lastUpdatedDateGMT) {
        this.lastUpdatedDateGMT = lastUpdatedDateGMT;
        return this;
    }

    public void setLastUpdatedDateGMT(ZonedDateTime lastUpdatedDateGMT) {
        this.lastUpdatedDateGMT = lastUpdatedDateGMT;
    }

    public Boolean isFeatured() {
        return featured;
    }

    public Post featured(Boolean featured) {
        this.featured = featured;
        return this;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean isSticky() {
        return sticky;
    }

    public Post sticky(Boolean sticky) {
        this.sticky = sticky;
        return this;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    public String getUpdates() {
        return updates;
    }

    public Post updates(String updates) {
        this.updates = updates;
        return this;
    }

    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public String getSlug() {
        return slug;
    }

    public Post slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public Post featuredImage(String featuredImage) {
        this.featuredImage = featuredImage;
        return this;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
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
        Post post = (Post) o;
        if (post.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Post{" +
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
            "}";
    }
}
