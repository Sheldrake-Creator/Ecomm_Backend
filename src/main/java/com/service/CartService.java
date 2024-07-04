package com.service;

import com.dto.CartDTO;
import com.exception.CartServiceException;

public interface CartService {

    CartDTO createCart(Long userId) throws CartServiceException;

    CartDTO getUserCart(Long userId) throws CartServiceException;

    CartDTO findUserCart(Long userId) throws CartServiceException;

    CartDTO findCartByCartId(Long cartId) throws CartServiceException;

    CartDTO syncCartWithCartItems(CartDTO cart) throws CartServiceException;

    void checkoutCart(Long cartId) throws CartServiceException;
}
