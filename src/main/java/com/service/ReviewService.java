package com.service;

import com.exception.ProductException;
import com.model.Review;
import com.model.User;
import com.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest request, User user) throws ProductException;

    List<Review> getAllReviews(Long ProductId);
}
