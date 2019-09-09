package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.DegaUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DegaUser and its DTO DegaUserDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class, RoleMappingMapper.class, MediaMapper.class})
public interface DegaUserMapper extends EntityMapper<DegaUserDTO, DegaUser> {

    @Mapping(source = "organizationDefault.id", target = "organizationDefaultId")
    @Mapping(source = "organizationDefault.name", target = "organizationDefaultName")
    @Mapping(source = "organizationCurrent.id", target = "organizationCurrentId")
    @Mapping(source = "organizationCurrent.name", target = "organizationCurrentName")
    @Mapping(source = "media", target = "media")
    DegaUserDTO toDto(DegaUser degaUser);

    @Mapping(source = "organizationDefaultId", target = "organizationDefault")
    @Mapping(source = "organizationCurrentId", target = "organizationCurrent")
    @Mapping(target = "posts", ignore = true)
    @Mapping(source = "media.id", target = "media")
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
