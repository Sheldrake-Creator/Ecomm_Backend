package com.response;

import com.dto.CartDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetCartResponse implements CartResponse{

    private CartDTO cart;

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

}

