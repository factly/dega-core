package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.OrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {MediaMapper.class})
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {

    @Mapping(source = "mediaLogo", target = "mediaLogoDTO")
    @Mapping(source = "mediaMobileLogo", target = "mediaMobileLogoDTO")
    @Mapping(source = "mediaFavicon", target = "mediaFaviconDTO")
    @Mapping(source = "mediaMobileIcon", target = "mediaMobileIconDTO")
    OrganizationDTO toDto(Organization organization);

    @Mapping(target = "degaUsers", ignore = true)
    @Mapping(target = "degaUserDefaults", ignore = true)
    @Mapping(target = "degaUserCurrents", ignore = true)
    @Mapping(target = "roleMappings", ignore = true)
    @Mapping(source = "mediaLogoDTO.id", target = "mediaLogo")
    @Mapping(source = "mediaMobileLogoDTO.id", target = "mediaMobileLogo")
    @Mapping(source = "mediaFaviconDTO.id", target = "mediaFavicon")
    @Mapping(source = "mediaMobileIconDTO.id", target = "mediaMobileIcon")
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
