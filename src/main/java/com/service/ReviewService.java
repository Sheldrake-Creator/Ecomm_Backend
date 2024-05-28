package com.service;

import com.exception.ProductException;
import com.exception.ReviewException;
import com.model.Rating;
import com.model.Review;
import com.model.User;
import com.request.CreateReviewRequest;
import com.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest request, User user) throws ProductException;

    public List<Review> getAllReviews(Long ProductId);
}
