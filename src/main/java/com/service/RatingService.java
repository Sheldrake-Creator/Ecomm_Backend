package com.service;

import com.exception.ProductException;
import com.model.Rating;
import com.model.User;
import com.request.RatingRequest;
import java.util.List;

public interface RatingService {

    Rating createRating(RatingRequest req, User user) throws ProductException;
    List<Rating> getProductsRating(Long productId);







}
