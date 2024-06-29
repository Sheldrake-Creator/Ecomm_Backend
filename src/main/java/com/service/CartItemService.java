package com.service;

import java.util.Optional;

import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.dto.ProductDTO;
import com.dto.UserDTO;
import com.exception.CartException;
import com.exception.CartItemException;
import com.exception.ProductException;
import com.exception.UserException;
import com.model.CartItem;

public interface CartItemService {

        CartItemDTO updateCartItem(Long userId, Long cartId, CartItemDTO cartItemDto)
                        throws CartItemException, UserException, CartException;

        boolean doesCartItemExist(CartDTO cart, ProductDTO product, String size) throws CartItemException;

        void removeCartItem(Long userId, Long cartItemId) throws CartItemException;

        CartItemDTO findCartItemById(Long cartItemId) throws CartItemException;

        CartDTO addItemToCart(UserDTO user, int quantity, String size, long productId)
                        throws CartItemException, CartException, ProductException, UserException;

}
