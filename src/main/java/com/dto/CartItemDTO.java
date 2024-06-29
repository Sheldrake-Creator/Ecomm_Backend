package com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {

    private Long cartItemId;
    private String size;
    private int quantity;
    private Integer price;
    private Integer discountedPrice;
    private ProductDTO product;
    private Long cartId;
}
