package com.factly.dega.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @DBRef
    @Field("degaUser")
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

    public Set<DegaUser> getDegaUsers() {
        return degaUsers;
    }

    public Role degaUsers(Set<DegaUser> degaUsers) {
        this.degaUsers = degaUsers;
        return this;
    }

    public Role addDegaUser(DegaUser degaUser) {
        this.degaUsers.add(degaUser);
        degaUser.setRole(this);
        return this;
    }

    public Role removeDegaUser(DegaUser degaUser) {
        this.degaUsers.remove(degaUser);
        degaUser.setRole(null);
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
            "}";
    }
}
