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
public class ProductDTO {

    private Long productId;
    private String description;
    private String title;
    private Integer price;
    private Integer discountedPrice;
    private Integer discountPresent;
    private Integer numInStock;
    private String brand;
    private String color;
    private String imageUrl;
    private Set<SizeDTO> sizes;
    // private List<RatingDTO> ratings;
    // private List<ReviewDTO> reviews;
    private Integer numRatings;
    private Long categoryId;

}
