package com.service;

import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.CartItemException;
import com.exception.ProductException;
import com.exception.UserException;

public interface CartService {

    CartDTO createCart(UserDTO user) throws CartException;

    CartDTO getUserCart(UserDTO user) throws UserException, CartException;

    String addItemToCart(Long userId, int quantity, String size, long productId)
            throws ProductException, CartException, CartItemException;

    CartDTO findUserCart(Long userId) throws CartException;
}
