package com.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne // * Recent Addition. 
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy ="cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name ="cart_items")
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name ="total_price")
    private double totalPrice;

    @Column(name="total_items")
    private int totalItems;

    private int totalDiscountedPrice;


    public Cart() {
    }

    public Cart(Long cartId, User user, Set<CartItem> cartItems, double totalPrice, int totalItems,
                int totalDiscountedPrice) {
        this.cartId = cartId;
        this.user = user;

        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
        this.totalDiscountedPrice = totalDiscountedPrice;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalDiscountedPrice() {
        return totalDiscountedPrice;
    }

    public void setTotalDiscountedPrice(int totalDiscountedPrice) {
        this.totalDiscountedPrice = totalDiscountedPrice;
    }
}
