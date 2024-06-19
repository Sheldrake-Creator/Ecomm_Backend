package com.service;

import java.util.List;

import com.dto.RatingDTO;
import com.dto.UserDTO;
import com.exception.ProductException;
import com.request.RatingRequest;

public interface RatingService {

    RatingDTO createRating(RatingRequest req, UserDTO user) throws ProductException;
    List<RatingDTO> getAllRatings(Long productId);
}
