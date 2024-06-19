package com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

}
