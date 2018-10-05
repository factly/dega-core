package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.OrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {


}
