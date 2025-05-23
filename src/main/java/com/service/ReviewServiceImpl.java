package com.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.ProductDTO;
import com.dto.ReviewDTO;
import com.dto.UserDTO;
import com.exception.ProductServiceException;
import com.exception.ReviewServiceException;
import com.mapper.ReviewMapper;
import com.model.Review;
import com.repository.ReviewRepository;
import com.request.ReviewRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ReviewMapper reviewMapper;
    private final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

    @Override
    public ReviewDTO createReview(ReviewRequest req, UserDTO user) throws ProductServiceException {

        logger.debug("Req: {}", req);
        logger.debug("UserDTO: {}", user);

        ProductDTO product = productService.findProductById(req.getProductId());

        ReviewDTO review = new ReviewDTO();
        review.setReview(req.getReview());
        review.setUserId(user.getUserId());
        review.setCreatedAt(LocalDateTime.now());
        review.setProductId(product.getProductId());
        this.reviewRepository.save(reviewMapper.toReview(review));
        return review;
    }

    @Override
    public List<ReviewDTO> getAllReviews(Long productId) throws ReviewServiceException {
        Optional<List<Review>> oReviews = reviewRepository.getAllProductReview(productId);
        List<ReviewDTO> reviewDtos = new ArrayList<>();
        if (!oReviews.isPresent()) {
            throw new ReviewServiceException("Product ID did not return any reviews");
        }
        List<Review> reviewEntities = oReviews.get();

        for (Review review : reviewEntities) {
            reviewDtos.add(reviewMapper.toReviewDTO(review));
        }
        return reviewDtos;
    }
}
