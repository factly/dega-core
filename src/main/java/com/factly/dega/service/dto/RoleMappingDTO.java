package com.factly.dega.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RoleMapping entity.
 */
public class RoleMappingDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            ", name='" + getName() + "'" +
            ", organization=" + getOrganizationId() +
            ", organization='" + getOrganizationName() + "'" +
            ", role=" + getRoleId() +
            ", role='" + getRoleName() + "'" +
            "}";
    }
}
