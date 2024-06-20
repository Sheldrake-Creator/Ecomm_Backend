package com.controller;

import com.dto.ReviewDTO;
import com.dto.UserDTO;
import com.exception.ProductException;
import com.exception.ReviewException;
import com.exception.UserException;
import com.request.ReviewRequest;
import com.response.HttpResponse;
import com.service.ReviewService;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createReviewReview(@RequestBody ReviewRequest req,
            @RequestHeader("Authorization") String jwt) {
        try {
            UserDTO user = userService.findUserProfileByJwt(jwt);
            ReviewDTO review = reviewService.createReview(req, user);
            logger.debug("Review created: {}", review);
            return ResponseEntity.status(HttpStatus.CREATED).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("review", review))
                    .message("Review created successfully")
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .build());
        } catch (UserException | ProductException e) {
            logger.error("Error creating review", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build());
        } catch (Exception e) {
            logger.error("Unexpected error during review creation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .message("Unexpected error during review creation")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<HttpResponse> getProductsReview(@PathVariable Long productId)
            throws ProductException, UserException {
        try {
            List<ReviewDTO> reviews = reviewService.getAllReviews(productId);
            logger.debug("Reviews fetched for product {}: {}", productId, reviews);
            return ResponseEntity.ok(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("reviews", reviews))
                    .message("Reviews fetched successfully")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .build());
        } catch (ReviewException e) {
            logger.error("Error fetching reviews for product {}", productId, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build());
        } catch (Exception e) {
            logger.error("Unexpected error fetching reviews for product {}", productId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .message("Unexpected error fetching reviews")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }
}
