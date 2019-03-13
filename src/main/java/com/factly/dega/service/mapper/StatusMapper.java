package com.factly.dega.service.mapper;

import com.factly.dega.domain.Status;
import com.factly.dega.service.dto.StatusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Status and its DTO StatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatusMapper extends EntityMapper<StatusDTO, Status> {


    @Mapping(target = "posts", ignore = true)
    Status toEntity(StatusDTO statusDTO);

    default Status fromId(String id) {
        if (id == null) {
            return null;
        }
        Status status = new Status();
        status.setId(id);
        return status;
    }
}
