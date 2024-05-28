package com.service;

import com.exception.ProductException;
import com.exception.ReviewException;
import com.model.Rating;
import com.model.User;
import com.request.CreateProductRequest;
import com.request.CreateReviewRequest;
import com.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);







}
