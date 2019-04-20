package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.RoleMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RoleMapping and its DTO RoleMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class, RoleMapper.class})
public interface RoleMappingMapper extends EntityMapper<RoleMappingDTO, RoleMapping> {

    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.name", target = "organizationName")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    RoleMappingDTO toDto(RoleMapping roleMapping);

    @Mapping(source = "organizationId", target = "organization")
    @Mapping(source = "roleId", target = "role")
    @Mapping(target = "degaUsers", ignore = true)
    RoleMapping toEntity(RoleMappingDTO roleMappingDTO);

    default RoleMapping fromId(String id) {
        if (id == null) {
            return null;
        }
        RoleMapping roleMapping = new RoleMapping();
        roleMapping.setId(id);
        return roleMapping;
    }
}
