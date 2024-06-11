package com.service;

import com.dto.CartDTO;
import com.dto.UserDTO;
import com.exception.ProductException;
import com.model.Cart;
import com.model.User;
import com.request.AddItemRequest;

public interface CartService {

    CartDTO createCart(UserDTO user);
    String addItemToCart(Long userId, AddItemRequest req) throws ProductException;

    Cart findUserCart(Long userId);
}
