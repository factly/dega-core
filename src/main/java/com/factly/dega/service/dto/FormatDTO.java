package com.factly.dega.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Format entity.
 */
public class FormatDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private Boolean isDefault;

    private String clientId;

    private String description;

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

    public Boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormatDTO formatDTO = (FormatDTO) o;
        if (formatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormatDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isDefault='" + isIsDefault() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", description='" + getDescription() + "'" +
            ", slug='" + getSlug() + "'" +
            "}";
    }
}
