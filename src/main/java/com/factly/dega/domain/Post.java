package com.factly.dega.domain;

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

    @Field("published_date")
    private ZonedDateTime publishedDate;

    @NotNull
    @Field("last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @Field("featured")
    private Boolean featured;

    @Field("sticky")
    private Boolean sticky;

    @Field("updates")
    private String updates;

    @NotNull
    @Field("slug")
    private String slug;

    @Field("password")
    private String password;

    @Field("sub_title")
    private String subTitle;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @DBRef
    @Field("tags")
    private Set<Tag> tags = new HashSet<>();

    @DBRef
    @Field("categories")
    private Set<Category> categories = new HashSet<>();

    @DBRef
    @Field("status")
    @JsonIgnoreProperties("posts")
    private Status status;

    @DBRef
    @Field("format")
    @JsonIgnoreProperties("posts")
    private Format format;

    @DBRef
    @Field("degaUsers")
    private Set<DegaUser> degaUsers = new HashSet<>();

    @DBRef
    @Field("media")
    @JsonIgnoreProperties("")
    private Media media;

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

    public String getPassword() {
        return password;
    }

    public Post password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Post subTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Post createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Post tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Post addTag(Tag tag) {
        this.tags.add(tag);
        tag.getPosts().add(this);
        return this;
    }

    public Post removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getPosts().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Post categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Post addCategory(Category category) {
        this.categories.add(category);
        category.getPosts().add(this);
        return this;
    }

    public Post removeCategory(Category category) {
        this.categories.remove(category);
        category.getPosts().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Status getStatus() {
        return status;
    }

    public Post status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Format getFormat() {
        return format;
    }

    public Post format(Format format) {
        this.format = format;
        return this;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Set<DegaUser> getDegaUsers() {
        return degaUsers;
    }

    public Post degaUsers(Set<DegaUser> degaUsers) {
        this.degaUsers = degaUsers;
        return this;
    }

    public Post addDegaUser(DegaUser degaUser) {
        this.degaUsers.add(degaUser);
        degaUser.getPosts().add(this);
        return this;
    }

    public Post removeDegaUser(DegaUser degaUser) {
        this.degaUsers.remove(degaUser);
        degaUser.getPosts().remove(this);
        return this;
    }

    public void setDegaUsers(Set<DegaUser> degaUsers) {
        this.degaUsers = degaUsers;
    }

    public Media getMedia() {
        return media;
    }

    public Post media(Media media) {
        this.media = media;
        return this;
    }

    public void setMedia(Media media) {
        this.media = media;
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
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", featured='" + isFeatured() + "'" +
            ", sticky='" + isSticky() + "'" +
            ", updates='" + getUpdates() + "'" +
            ", slug='" + getSlug() + "'" +
            ", password='" + getPassword() + "'" +
            ", subTitle='" + getSubTitle() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
