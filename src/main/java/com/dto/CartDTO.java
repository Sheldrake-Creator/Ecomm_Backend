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
    private Integer totalItems;
    private Integer totalPrice;
    private Integer totalDiscountedPrice;
    private long userId;
}
