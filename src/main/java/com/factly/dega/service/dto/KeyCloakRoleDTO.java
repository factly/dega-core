package com.factly.dega.service.dto;

import java.io.Serializable;

/**
 * A DTO for the DegaUser entity.
 */
public class KeyCloakRoleDTO implements Serializable {

    private String id;

    private String name;

    private String description;

    private Boolean composite;

    private Boolean clientRole;

    private String containerId;

    public KeyCloakRoleDTO(String id, String name, String description, Boolean composite, Boolean clientRole, String containerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.composite = composite;
        this.clientRole = clientRole;
        this.containerId = containerId;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getComposite() {
        return composite;
    }

    public void setComposite(Boolean composite) {
        this.composite = composite;
    }

    public Boolean getClientRole() {
        return clientRole;
    }

    public void setClientRole(Boolean clientRole) {
        this.clientRole = clientRole;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyCloakRoleDTO that = (KeyCloakRoleDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (composite != null ? !composite.equals(that.composite) : that.composite != null) return false;
        if (clientRole != null ? !clientRole.equals(that.clientRole) : that.clientRole != null) return false;
        return containerId != null ? containerId.equals(that.containerId) : that.containerId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (composite != null ? composite.hashCode() : 0);
        result = 31 * result + (clientRole != null ? clientRole.hashCode() : 0);
        result = 31 * result + (containerId != null ? containerId.hashCode() : 0);
        return result;
    }
}
