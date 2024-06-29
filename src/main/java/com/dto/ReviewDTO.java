package com.dto;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.LongAdder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private String review;
    private Long productId;
    private Long userId;
    LocalDateTime createdAt;
}
