package com.service;

import java.util.List;
import com.dto.CategoryDTO;

public interface CategoryService {

    public List<CategoryDTO> findCategoriesByLevel(Integer level);

}
