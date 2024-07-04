package com.service;

import java.util.List;

import com.dto.RatingDTO;
import com.dto.UserDTO;
import com.exception.ProductServiceException;
import com.exception.RatingServiceException;
import com.request.RatingRequest;

public interface RatingService {

    RatingDTO createRating(RatingRequest req, UserDTO user) throws ProductServiceException;

    List<RatingDTO> getAllRatings(Long productId) throws RatingServiceException;
}
