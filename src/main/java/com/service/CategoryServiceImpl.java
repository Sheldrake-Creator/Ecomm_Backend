package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.CategoryDTO;
import com.mapper.CategoryMapper;
import com.model.Category;
import com.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> findCategoriesByLevel(Integer level) {
        List<Category> categoriesEntities = categoryRepository.getAllCategoriesByLevel(level);
        List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
        for (Category category : categoriesEntities) {
            categoryDTOs.add(categoryMapper.toCategoryDTO(category));
        }
        if (!categoryDTOs.isEmpty()) {
            return categoryDTOs;
        }
        throw new IllegalStateException();
    }

}
