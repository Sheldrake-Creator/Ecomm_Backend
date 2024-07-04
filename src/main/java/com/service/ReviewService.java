package com.service;

import java.util.List;

import com.dto.ReviewDTO;
import com.dto.UserDTO;
import com.exception.ProductServiceException;
import com.exception.ReviewServiceException;
import com.request.ReviewRequest;

public interface ReviewService {

    List<ReviewDTO> getAllReviews(Long ProductId) throws ReviewServiceException;

    ReviewDTO createReview(ReviewRequest req, UserDTO user) throws ProductServiceException;
}
