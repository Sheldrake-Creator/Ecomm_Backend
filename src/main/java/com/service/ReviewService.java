package com.service;

import java.util.List;

import com.dto.ReviewDTO;
import com.dto.UserDTO;
import com.exception.ProductException;
import com.request.ReviewRequest;

public interface ReviewService {

    List<ReviewDTO> getAllReviews(Long ProductId);

    ReviewDTO createReview(ReviewRequest req, UserDTO user)throws ProductException;
}
