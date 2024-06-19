package com.mapper;

import org.mapstruct.Mapper;

import com.dto.CategoryDTO;
import com.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toCategoryDTO(Category category);

    Category toCategory(CategoryDTO categoryDto);
}
