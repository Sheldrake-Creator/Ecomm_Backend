package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.CategoryDTO;
import com.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentCategoryId", source = "parentCategory.categoryId") // Add this line
    CategoryDTO toCategoryDTO(Category category);

    @Mapping(target = "parentCategory.categoryId", source = "parentCategoryId") // Add this line
    Category toCategory(CategoryDTO categoryDto);
}
