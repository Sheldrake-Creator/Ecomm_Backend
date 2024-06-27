package com.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    private Long ratingId;
    private double rating;
    private ProductDTO product;
    private UserDTO user;
    private LocalDateTime createdAt;  
}
