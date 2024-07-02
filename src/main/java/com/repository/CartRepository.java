package com.repository;

import com.exception.RepositoryException;
import com.model.Cart;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c From Cart c Where c.user.id=:userId")
    Optional<Cart> findByUserId(@Param("userId") Long userId) throws DataAccessException, RepositoryException;

    @Modifying
    @Query("SELECT c From Cart c Where c.cartId=:cartId")
    void deleteCartById(@Param("cartId") Long userId) throws RepositoryException;
}
