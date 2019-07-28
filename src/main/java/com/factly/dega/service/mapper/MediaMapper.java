package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.MediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Media and its DTO MediaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {


    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "degaUsers", ignore = true)
    @Mapping(target = "organizationLogos", ignore = true)
    @Mapping(target = "organizationMobileLogos", ignore = true)
    @Mapping(target = "organizationFavicons", ignore = true)
    @Mapping(target = "organizationMobileIcons", ignore = true)
    Media toEntity(MediaDTO mediaDTO);

    default Media fromId(String id) {
        if (id == null) {
            return null;
        }
        Media media = new Media();
        media.setId(id);
        return media;
    }
}
