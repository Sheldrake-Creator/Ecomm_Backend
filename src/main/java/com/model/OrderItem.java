package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @JsonIgnore
    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;
    private String size;
    private int quantity;
    private Integer orderPrice;
    private Integer discountedPrice;
    private Long userId;
    private LocalDateTime deliveryDate;
}
