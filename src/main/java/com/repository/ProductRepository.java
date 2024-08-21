package com.repository;

import com.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

        @Query("SELECT p FROM Product p WHERE (:categories IS NULL OR p.category.name IN :categories)")
        Optional<List<Product>> filterProducts(@Param("categories") List<String> categories);

        @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
        Optional<List<Product>> singleSubSearch(@Param("categoryId") Long categoryId);

        @Query("SELECT p FROM Product p JOIN p.category c WHERE c.parentCategory.categoryId = :categoryId1 OR c.parentCategory.categoryId = :categoryId2")
        Optional<List<Product>> getByRealOrFake(@Param("categoryId1") Long categoryId1,
                        @Param("categoryId2") Long categoryId2);

        @Query("SELECT p FROM Product p WHERE p.brand IN :brands")
        Optional<List<Product>> findByBrand(@Param("brands") List<String> brands);

        @Query("SELECT p FROM Product p WHERE p.veracity = true")
        List<Product> getAllReal();

        @Query("SELECT p FROM Product p WHERE p.veracity = false")
        List<Product> getAllFake();

}
