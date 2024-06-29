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
    private double rating;
    private Long productId;
    private Long userId;
    private LocalDateTime createdAt;
}
