package com.service;

import com.exception.ProductException;
import com.exception.ReviewException;
import com.model.Product;
import com.model.Rating;
import com.model.Review;
import com.model.User;
import com.repository.ProductRepository;
import com.repository.ReviewRepository;
import com.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Review review= new Review();
        review.setReview(req.getReview());
        review.setUser(user);
        review.setCreatedAt(LocalDateTime.now());
        review.setProduct(product);

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews(Long productId) {
        return reviewRepository.getAllProductReview(productId);
    }
}
