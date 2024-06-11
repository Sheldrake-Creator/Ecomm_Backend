package com.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private long cartId;
    private long userId;
    private int totalDiscountedPrice;
    private int totalItems;
    private double totalPrice;
}
