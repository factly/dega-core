package com.factly.dega.service.mapper;

import com.factly.dega.domain.*;
import com.factly.dega.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Post and its DTO PostDTO.
 */
@Mapper(componentModel = "spring", uses = {TagMapper.class, CategoryMapper.class, StatusMapper.class, FormatMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "statusName")
    @Mapping(source = "format.id", target = "formatId")
    @Mapping(source = "format.name", target = "formatName")
    PostDTO toDto(Post post);

    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "formatId", target = "format")
    Post toEntity(PostDTO postDTO);

    default Post fromId(String id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
