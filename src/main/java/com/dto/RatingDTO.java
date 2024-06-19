package com.dto;

import java.time.LocalDateTime;

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
    private UserDTO user;
    private ProductDTO product;
    private double rating;
    private LocalDateTime createdAt;
    
}
