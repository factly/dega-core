package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.TagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tag and its DTO TagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagMapper extends EntityMapper<TagDTO, Tag> {


}
