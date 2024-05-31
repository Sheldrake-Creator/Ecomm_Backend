package com.service;

import com.exception.ProductException;
import com.model.Cart;
import com.model.User;
import com.request.AddItemRequest;

public interface CartService {

    Cart createCart(User user);
    String addItemToCart(Long userId, AddItemRequest req) throws ProductException;

    Cart findUserCart(Long userId);
}
