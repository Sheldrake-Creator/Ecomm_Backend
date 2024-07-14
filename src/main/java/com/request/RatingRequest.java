package com.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class RatingRequest {

    private Long productId;
    private double rating;
}
