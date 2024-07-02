package com.service;

import com.dto.CartDTO;
import com.exception.CartException;

public interface CartService {

    CartDTO createCart(Long userId) throws CartException;

    CartDTO getUserCart(Long userId) throws CartException;

    CartDTO findUserCart(Long userId) throws CartException;

    CartDTO findCartByCartId(Long cartId) throws CartException;

    CartDTO syncCartWithCartItems(CartDTO cart) throws CartException;

    void checkoutCart(Long cartId) throws CartException;
}
