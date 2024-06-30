package com.repository;

import com.exception.RepositoryException;
import com.model.Cart;
import com.model.CartItem;
import com.model.Product;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

        @Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product AND ci.size=:size")
        Optional<CartItem> doesCartItemExist(@Param("cart") Cart cart, @Param("product") Product product,
                        @Param("size") String size) throws RepositoryException;

        @Query("SELECT ci From CartItem ci Where ci.cartItemId=:cartItemId")
        Optional<CartItem> findCartItemById(@Param("cartItemId") Long cartItemId)
                        throws DataAccessException, RepositoryException;

        @Modifying
        @Query("DELETE FROM CartItem c WHERE c.cartItemId = :cartItemId")
        void deleteCartItemById(@Param("cartItemId") Long cartItemId);
}
