package com.service;

import com.exception.ProductException;
import com.model.Product;
import com.model.Rating;
import com.model.User;
import com.repository.RatingRepository;
import com.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService{

    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImpl(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());

        Rating rating = new Rating();
        rating.setRating(req.getRating());
        rating.setProduct(product);
        rating.setCreatedAt(LocalDateTime.now());
        rating.setUser(user);
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {

        return ratingRepository.getAllProductsRating(productId);
    }
}
