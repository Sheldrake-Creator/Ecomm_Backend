package com.service;

import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.CartException;

public interface CartService {

    CartDTO createCart(UserDTO user) throws CartException;

    CartDTO getUserCart(UserDTO user) throws CartException;

    CartDTO findUserCart(Long userId) throws CartException;

    // String addItemToCart(Long userId, int quantity, String size, long productId)
    // throws ProductException, CartException, CartItemException;

    CartDTO findCartByCartId(Long cartId) throws CartException;

    CartDTO syncCartWithCartItems(CartDTO cart) throws CartException;
}
