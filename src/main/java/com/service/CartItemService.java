package com.service;

import java.util.Optional;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.exception.CartItemException;
import com.exception.UserException;
import com.model.CartItem;

public interface CartItemService {

    CartItemDTO addCartItem(CartItem cartItem, long userId) throws CartItemException;

    CartItemDTO updateCartItem(Long userId, Long cartId, CartItemDTO cartItemDto)
            throws CartItemException, UserException;

    Optional<CartItem> doesCartItemExist(CartDTO cart, ProductDTO product, String size, Long userId) throws CartItemException;

    void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    CartItemDTO findCartItemById(Long cartItemId) throws CartItemException;

}
