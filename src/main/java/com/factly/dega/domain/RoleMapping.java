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

    @DBRef
    @Field("degaUser")
    @JsonIgnoreProperties("")
    private DegaUser degaUser;

    @DBRef
    @Field("organization")
    @JsonIgnoreProperties("")
    private Organization organization;

    @DBRef
    @Field("role")
    @JsonIgnoreProperties("")
    private Role role;

    @DBRef
    @Field("degaUserRoleMappings")
    @JsonIgnore
    private Set<DegaUser> degaUserRoleMappings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DegaUser getDegaUser() {
        return degaUser;
    }

    public RoleMapping degaUser(DegaUser degaUser) {
        this.degaUser = degaUser;
        return this;
    }

    public void setDegaUser(DegaUser degaUser) {
        this.degaUser = degaUser;
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

    public Set<DegaUser> getDegaUserRoleMappings() {
        return degaUserRoleMappings;
    }

    public RoleMapping degaUserRoleMappings(Set<DegaUser> degaUsers) {
        this.degaUserRoleMappings = degaUsers;
        return this;
    }

    public RoleMapping addDegaUserRoleMapping(DegaUser degaUser) {
        this.degaUserRoleMappings.add(degaUser);
        degaUser.getRoleMappingDegaUsers().add(this);
        return this;
    }

    public RoleMapping removeDegaUserRoleMapping(DegaUser degaUser) {
        this.degaUserRoleMappings.remove(degaUser);
        degaUser.getRoleMappingDegaUsers().remove(this);
        return this;
    }

    public void setDegaUserRoleMappings(Set<DegaUser> degaUsers) {
        this.degaUserRoleMappings = degaUsers;
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
            "}";
    }
}
