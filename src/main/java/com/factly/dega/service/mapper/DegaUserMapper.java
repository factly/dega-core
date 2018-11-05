package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.DegaUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DegaUser and its DTO DegaUserDTO.
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface DegaUserMapper extends EntityMapper<DegaUserDTO, DegaUser> {

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    DegaUserDTO toDto(DegaUser degaUser);

    @Mapping(source = "roleId", target = "role")
    DegaUser toEntity(DegaUserDTO degaUserDTO);

    default DegaUser fromId(String id) {
        if (id == null) {
            return null;
        }
        DegaUser degaUser = new DegaUser();
        degaUser.setId(id);
        return degaUser;
    }
}
