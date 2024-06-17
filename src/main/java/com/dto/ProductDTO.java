package com.dto;

import java.util.List;
import java.util.Set;

import com.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;
    private String description;
    private String title;
    private int price;
    private int discountedPrice;
    private int discountPresent;
    private int numInStock;
    private String brand;
    private String color;
    private String imageUrl;
    // private Set<SizeDTO> sizes;
    // private List<RatingDTO> ratings;
    // private List<ReviewDTO> reviews;
    private int numRatings;
    private Category category;


}
