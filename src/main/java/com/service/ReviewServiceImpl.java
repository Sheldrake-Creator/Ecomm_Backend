package com.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.ProductDTO;
import com.dto.ReviewDTO;
import com.dto.UserDTO;
import com.exception.ProductException;
import com.mapper.ReviewMapper;
import com.model.Review;
import com.repository.ReviewRepository;
import com.request.ReviewRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ReviewMapper reviewMapper;

    @Override
    public ReviewDTO createReview(ReviewRequest req, UserDTO user) throws ProductException {
        ProductDTO product = productService.findProductById(req.getProductId());

        ReviewDTO review = new ReviewDTO();
        review.setReview(req.getReview());
        review.setUser(user);
        review.setCreatedAt(LocalDateTime.now());
        review.setProduct(product);
        this.reviewRepository.save(reviewMapper.toReview(review));
        return review;
    }

    @Override
    public List<ReviewDTO> getAllReviews(Long productId) {
        List<Review> reviews = reviewRepository.getAllProductReview(productId);
        List<ReviewDTO> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(reviewMapper.toReviewDTO(review));
        }
        return reviewDtos;
    }
}
