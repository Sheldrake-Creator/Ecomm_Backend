package com.service;
import com.dto.CartDTO;
import com.dto.CartItemDTO;
import com.exception.CartException;
import com.exception.CartItemException;
import com.exception.UserException;
import com.model.Cart;
import com.model.CartItem;
import com.model.Product;


public interface CartItemService {

    CartItemDTO addCartItem(CartItem cartItem, long userId);
    CartItemDTO updateCartItem(Long userId, Long cartId, CartItemDTO cartItemDto)throws CartItemException, UserException;
    CartItem doesCartItemExist(Cart cart, Product product, String size, Long userId);
    void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
    CartItemDTO findCartItemById(Long cartItemId)throws CartItemException;
}
