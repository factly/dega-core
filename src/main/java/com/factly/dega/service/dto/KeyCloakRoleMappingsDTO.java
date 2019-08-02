package com.factly.dega.service.dto;

import java.util.List;

/**
 * Created by ntalla on 8/1/19.
 */
public class KeyCloakRoleMappingsDTO {

    private List<KeyCloakRoleMappingDTO> keyCloakRoleMappingsDTO;

    public List<KeyCloakRoleMappingDTO> getKeyCloakRoleMappingsDTO() {
        return keyCloakRoleMappingsDTO;
    }

    public void setKeyCloakRoleMappingsDTO(List<KeyCloakRoleMappingDTO> keyCloakRoleMappingsDTO) {
        this.keyCloakRoleMappingsDTO = keyCloakRoleMappingsDTO;
    }
}
