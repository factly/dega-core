package com.factly.dega.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RoleMapping entity.
 */
public class RoleMappingDTO implements Serializable {

    private String id;

    private String degaUserId;

    private String degaUserDisplayName;

    private String organizationId;

    private String organizationName;

    private String roleId;

    private String roleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDegaUserId() {
        return degaUserId;
    }

    public void setDegaUserId(String degaUserId) {
        this.degaUserId = degaUserId;
    }

    public String getDegaUserDisplayName() {
        return degaUserDisplayName;
    }

    public void setDegaUserDisplayName(String degaUserDisplayName) {
        this.degaUserDisplayName = degaUserDisplayName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoleMappingDTO roleMappingDTO = (RoleMappingDTO) o;
        if (roleMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleMappingDTO{" +
            "id=" + getId() +
            ", degaUser=" + getDegaUserId() +
            ", degaUser='" + getDegaUserDisplayName() + "'" +
            ", organization=" + getOrganizationId() +
            ", organization='" + getOrganizationName() + "'" +
            ", role=" + getRoleId() +
            ", role='" + getRoleName() + "'" +
            "}";
    }
}
