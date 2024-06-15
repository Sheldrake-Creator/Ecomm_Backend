package com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long productId;
    private String title;
    private int price;
    private int discountedPrice;
    private int discountPresent;
    private int quantity;
    private String brand;
    private String color;
    private String imageUrl;
    private int numRatings;

}
