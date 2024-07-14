package com.repository;

import com.exception.RepositoryException;
import com.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("Select r from Review r where r.product.id=:productId")
    Optional<List<Review>> getAllProductReview(@Param("productId") Long productId) throws RepositoryException;

}
