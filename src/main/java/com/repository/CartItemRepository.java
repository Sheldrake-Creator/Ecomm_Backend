package com.repository;

import com.model.Cart;
import com.model.CartItem;
import com.model.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product AND ci.size=:size AND ci.userId=:userId")
    Optional<CartItem> doesCartItemExist(@Param("cart") Cart cart, @Param("product") Product product,
            @Param("size") String size,
            @Param("userId") Long userId);
}
