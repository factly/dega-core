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
 * A Role.
 */
@Document(collection = "role")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("client_id")
    private String clientId;

    @Field("is_default")
    private Boolean isDefault;

    @NotNull
    @Field("slug")
    private String slug;

    @NotNull
    @Field("created_date")
    private ZonedDateTime createdDate;

    @NotNull
    @Field("last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @Field("keycloak_id")
    private String keycloakId;

    @Field("keycloak_name")
    private String keycloakName;

    @DBRef
    @Field("roleMapping")
    private Set<RoleMapping> roleMappings = new HashSet<>();
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

    public Role name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public Role clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean isIsDefault() {
        return isDefault;
    }

    public Role isDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getSlug() {
        return slug;
    }

    public Role slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Role createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Role lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public Role keycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
        return this;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public String getKeycloakName() {
        return keycloakName;
    }

    public Role keycloakName(String keycloakName) {
        this.keycloakName = keycloakName;
        return this;
    }

    public void setKeycloakName(String keycloakName) {
        this.keycloakName = keycloakName;
    }

    public Set<RoleMapping> getRoleMappings() {
        return roleMappings;
    }

    public Role roleMappings(Set<RoleMapping> roleMappings) {
        this.roleMappings = roleMappings;
        return this;
    }

    public Role addRoleMapping(RoleMapping roleMapping) {
        this.roleMappings.add(roleMapping);
        roleMapping.setRole(this);
        return this;
    }

    public Role removeRoleMapping(RoleMapping roleMapping) {
        this.roleMappings.remove(roleMapping);
        roleMapping.setRole(null);
        return this;
    }

    public void setRoleMappings(Set<RoleMapping> roleMappings) {
        this.roleMappings = roleMappings;
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
        Role role = (Role) o;
        if (role.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", isDefault='" + isIsDefault() + "'" +
            ", slug='" + getSlug() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", keycloakId='" + getKeycloakId() + "'" +
            ", keycloakName='" + getKeycloakName() + "'" +
            "}";
    }
}
