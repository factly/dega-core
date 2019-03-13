package com.factly.dega.service.mapper;

import com.factly.dega.domain.Category;
import com.factly.dega.service.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {


    @Mapping(target = "posts", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(String id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
