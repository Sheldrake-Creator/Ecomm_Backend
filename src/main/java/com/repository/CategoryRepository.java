package com.repository;

import com.exception.RepositoryException;
import com.model.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name) throws RepositoryException;;

    @Query("Select c from Category c Where c.name=:name And c.parentCategory.name=:parentCategoryName")
    Optional<Category> findByNameAndParent(@Param("name") String name,
            @Param("parentCategoryName") String parentCategoryName) throws RepositoryException;

    @Query("SELECT c FROM Category c WHERE c.level= :level")
    List<Category> getAllCategoriesByLevel(@Param("level") Integer level) throws RepositoryException;

}
