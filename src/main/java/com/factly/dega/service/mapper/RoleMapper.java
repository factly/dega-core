package com.factly.dega.service.mapper;

import com.factly.dega.domain.Role;
import com.factly.dega.service.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Role and its DTO RoleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {


    @Mapping(target = "degaUsers", ignore = true)
    Role toEntity(RoleDTO roleDTO);

    default Role fromId(String id) {
        if (id == null) {
            return null;
        }
        Role role = new Role();
        role.setId(id);
        return role;
    }
}
