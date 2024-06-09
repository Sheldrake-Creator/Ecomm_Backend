package com.controller;

import com.exception.ProductException;
import com.exception.UserException;
import com.model.Review;
import com.model.User;
import com.request.ReviewRequest;
import com.service.ReviewService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    UserService userService;
    @Autowired
    ReviewService reviewService;


    public ReviewController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<Review> createReviewReview (@RequestBody ReviewRequest req,
                                                @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user=userService.findUserProfileByJwt(jwt);
        Review review=reviewService.createReview(req, user);
        return new ResponseEntity<>(review,HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long productId)
throws UserException, ProductException{

        List<Review> reviews =reviewService.getAllReviews(productId);
        return new ResponseEntity<>(reviews, HttpStatus.CREATED);
    }
}
