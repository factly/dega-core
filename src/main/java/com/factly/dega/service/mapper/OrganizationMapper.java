package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.OrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {MediaMapper.class})
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {

    @Mapping(source = "mediaLogo", target = "mediaLogo")
    @Mapping(source = "mediaMobileLogo", target = "mediaMobileLogo")
    @Mapping(source = "mediaFavicon", target = "mediaFavicon")
    @Mapping(source = "mediaMobileIcon", target = "mediaMobileIcon")
    OrganizationDTO toDto(Organization organization);

    @Mapping(target = "degaUsers", ignore = true)
    @Mapping(target = "degaUserDefaults", ignore = true)
    @Mapping(target = "degaUserCurrents", ignore = true)
    @Mapping(target = "roleMappings", ignore = true)
    @Mapping(source = "mediaLogo.id", target = "mediaLogo")
    @Mapping(source = "mediaMobileLogo.id", target = "mediaMobileLogo")
    @Mapping(source = "mediaFavicon.id", target = "mediaFavicon")
    @Mapping(source = "mediaMobileIcon.id", target = "mediaMobileIcon")
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
