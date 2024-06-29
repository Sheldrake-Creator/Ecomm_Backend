package com.service;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.CartItemException;
import com.exception.ProductException;

public interface CartItemService {

        CartItemDTO updateCartItem(Long cartId, CartItemDTO cartItemDto) throws CartItemException, CartException;

        boolean doesCartItemExist(CartDTO cart, ProductDTO product, String size) throws CartItemException;

        void removeCartItem(Long userId, Long cartItemId) throws CartItemException;

        CartItemDTO findCartItemById(Long cartItemId) throws CartItemException;

        CartDTO addItemToCart(UserDTO user, int quantity, String size, long productId)
                        throws CartItemException, CartException, ProductException;

}
