package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.OrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {


    @Mapping(target = "degaUsers", ignore = true)
    @Mapping(target = "degaUserDefaults", ignore = true)
    @Mapping(target = "degaUserCurrents", ignore = true)
    Organization toEntity(OrganizationDTO organizationDTO);

    default Organization fromId(String id) {
        if (id == null) {
            return null;
        }
        Organization organization = new Organization();
        organization.setId(id);
        return organization;
    }
}
