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

    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long id, CartItem cartltem)throws CartItemException, UserException;
    public CartItem doesCartItemExist(Cart cart, Product product, String size, Long userId);
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
    public CartItem findCartItemById(Long cartItemId)throws CartItemException;
}
