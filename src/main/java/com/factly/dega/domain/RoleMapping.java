package com.factly.dega.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RoleMapping.
 */
@Document(collection = "role_mapping")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rolemapping")
public class RoleMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("keycloak_id")
    private String keycloakId;

    @NotNull
    @Field("keycloak_name")
    private String keycloakName;

    @DBRef
    @Field("organization")
    @JsonIgnoreProperties("roleMappings")
    private Organization organization;

    @DBRef
    @Field("role")
    @JsonIgnoreProperties("roleMappings")
    private Role role;

    @DBRef
    @Field("degaUsers")
    @JsonIgnore
    private Set<DegaUser> degaUsers = new HashSet<>();

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

    public RoleMapping name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public RoleMapping keycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
        return this;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public String getKeycloakName() {
        return keycloakName;
    }

    public RoleMapping keycloakName(String keycloakName) {
        this.keycloakName = keycloakName;
        return this;
    }

    public void setKeycloakName(String keycloakName) {
        this.keycloakName = keycloakName;
    }

    public Organization getOrganization() {
        return organization;
    }

    public RoleMapping organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Role getRole() {
        return role;
    }

    public RoleMapping role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<DegaUser> getDegaUsers() {
        return degaUsers;
    }

    public RoleMapping degaUsers(Set<DegaUser> degaUsers) {
        this.degaUsers = degaUsers;
        return this;
    }

    public RoleMapping addDegaUser(DegaUser degaUser) {
        this.degaUsers.add(degaUser);
        degaUser.getRoleMappings().add(this);
        return this;
    }

    public RoleMapping removeDegaUser(DegaUser degaUser) {
        this.degaUsers.remove(degaUser);
        degaUser.getRoleMappings().remove(this);
        return this;
    }

    public void setDegaUsers(Set<DegaUser> degaUsers) {
        this.degaUsers = degaUsers;
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
        RoleMapping roleMapping = (RoleMapping) o;
        if (roleMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleMapping{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", keycloakId='" + getKeycloakId() + "'" +
            ", keycloakName='" + getKeycloakName() + "'" +
            "}";
    }
}
