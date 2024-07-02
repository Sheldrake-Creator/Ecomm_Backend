package com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartItemDTO {

    private Long cartItemId;
    private String size;
    private Integer quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long productId;
    private Long cartId;
}
