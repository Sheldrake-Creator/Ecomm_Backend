package com.service;

import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.ProductException;

public interface CartService {

    CartDTO createCart(UserDTO user)throws CartException;
    
    CartDTO getUserCart(UserDTO user) throws CartException;

    String addItemToCart(Long userId, int quantity, String size, long productId) throws ProductException;

    CartDTO findUserCart(Long userId);
}
