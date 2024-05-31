package com.service;

import com.exception.CartItemException;
import com.exception.ProductException;
import com.exception.UserException;
import com.model.Cart;
import com.model.CartItem;
import com.model.Product;
import com.model.User;
import com.request.AddItemRequest;

public interface CartItemService {

    CartItem createCartItem(CartItem cartItem);
    CartItem updateCartItem(Long userId, Long id, CartItem cartltem)throws CartItemException, UserException;
    CartItem doesCartItemExist(Cart cart, Product product, String size, Long userId);
    void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
    CartItem findCartItemById(Long cartItemId)throws CartItemException;
}
