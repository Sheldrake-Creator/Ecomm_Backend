package com.request;

import com.model.Rating;

public class CreateReviewRequest {

    private Long productId;
    private double rating;

    public CreateReviewRequest() {
    }

    public CreateReviewRequest(Long productId, double rating) {
        this.productId = productId;
        this.rating = rating;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
