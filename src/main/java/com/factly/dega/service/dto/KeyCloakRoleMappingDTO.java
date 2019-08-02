package com.factly.dega.service.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "composite",
    "clientRole",
    "containerId"
})
public class KeyCloakRoleMappingDTO {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("composite")
    private Boolean composite;
    @JsonProperty("clientRole")
    private Boolean clientRole;
    @JsonProperty("containerId")
    private String containerId;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("composite")
    public Boolean getComposite() {
        return composite;
    }

    @JsonProperty("composite")
    public void setComposite(Boolean composite) {
        this.composite = composite;
    }

    @JsonProperty("clientRole")
    public Boolean getClientRole() {
        return clientRole;
    }

    @JsonProperty("clientRole")
    public void setClientRole(Boolean clientRole) {
        this.clientRole = clientRole;
    }

    @JsonProperty("containerId")
    public String getContainerId() {
        return containerId;
    }

    @JsonProperty("containerId")
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
}
