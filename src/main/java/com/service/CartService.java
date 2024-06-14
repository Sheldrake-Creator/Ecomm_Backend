package com.service;

import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.ProductException;
import com.model.Cart;
import com.request.AddItemRequest;

public interface CartService {

    CartDTO createCart(UserDTO user)throws CartException;
    
    CartDTO getUserCart(UserDTO user) throws CartException;

    String addItemToCart(AddItemRequest req) throws ProductException;

    Cart findUserCart(Long userId);
}
