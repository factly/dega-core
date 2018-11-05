package com.factly.dega.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Status entity.
 */
public class StatusDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String clientId;

    private Boolean isDefault;

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatusDTO statusDTO = (StatusDTO) o;
        if (statusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StatusDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", isDefault='" + isIsDefault() + "'" +
            "}";
    }
}
