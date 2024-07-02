package com.service;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.exception.CartItemException;

public interface CartItemService {

        CartItemDTO updateCartItem(Long cartId, CartItemDTO cartItemDto) throws CartItemException;

        boolean doesCartItemExist(CartDTO cart, ProductDTO product, String size) throws CartItemException;

        CartDTO removeCartItem(Long userId, Long cartItemId) throws CartItemException;

        CartItemDTO findCartItemById(Long cartItemId) throws CartItemException;

        CartDTO addItemToCart(Long userId, Integer quantity, String size, long productId) throws CartItemException;

}
