package com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDTO {

    private Long ratingId;
    // private UserDTO userDto;
    // private ProductDTO productDto;
    private double rating;
    

}
