package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ReviewDTO;
import com.dto.UserDTO;
import com.exception.ProductException;
import com.exception.UserException;
import com.request.ReviewRequest;
import com.service.ReviewService;
import com.service.UserService;

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
    public ResponseEntity<ReviewDTO> createReviewReview (@RequestBody ReviewRequest req,
                                                @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        UserDTO user=userService.findUserProfileByJwt(jwt);
        ReviewDTO review=reviewService.createReview(req, user);
        return new ResponseEntity<>(review,HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getProductsReview(@PathVariable Long productId)
throws UserException, ProductException{

        List<ReviewDTO> reviews =reviewService.getAllReviews(productId);
        return new ResponseEntity<>(reviews, HttpStatus.CREATED);
    }
}
