package com.response;

import com.dto.CartDTO;


public class CartResponse {

    private CartDTO cart;

        public CartResponse(CartDTO cart) {
            this.cart = cart;
    }

    public CartDTO getUser() {
        return cart;
    }

    public void setUser(CartDTO cart) {
        this.cart = cart;
    }

}
