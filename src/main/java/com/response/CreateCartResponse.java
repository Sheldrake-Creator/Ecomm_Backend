package com.response;

import com.dto.CartDTO;


public class CreateCartResponse implements CartResponse{

    private CartDTO cart;

        public CreateCartResponse(CartDTO cart) {
            this.cart = cart;
        }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

}
