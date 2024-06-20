package com.repository;

import com.model.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query("Select c from Category c Where c.name=:name And c.parentCategory.name=:parentCategoryName")
    Optional<Category> findByNameAndParent(@Param("name") String name,
            @Param("parentCategoryName") String parentCategoryName);
}
