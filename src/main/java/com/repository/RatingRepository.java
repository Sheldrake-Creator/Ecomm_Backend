package com.repository;

import com.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r From Rating r Where r.product.id=:productId")
    Optional<List<Rating>> getAllProductsRating(@Param("productId") Long productId);
}
