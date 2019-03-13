package com.factly.dega.service.mapper;

import com.factly.dega.domain.Format;
import com.factly.dega.service.dto.FormatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Format and its DTO FormatDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormatMapper extends EntityMapper<FormatDTO, Format> {


    @Mapping(target = "posts", ignore = true)
    Format toEntity(FormatDTO formatDTO);

    default Format fromId(String id) {
        if (id == null) {
            return null;
        }
        Format format = new Format();
        format.setId(id);
        return format;
    }
}
