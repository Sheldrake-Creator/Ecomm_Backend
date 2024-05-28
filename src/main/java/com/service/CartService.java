package com.service;

import com.exception.ProductException;
import com.model.Cart;
import com.model.User;
import com.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);
    public String addItemToCart(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);
}
