package com.dto;



import java.util.Set;
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
    private Set<CartItemDTO> cartItems;
    private int totalItems;
    private double totalPrice;
    private int totalDiscountedPrice;
    private long userId; 
}
