package com.controller;

import com.dto.RatingDTO;
import com.dto.UserDTO;
import com.exception.ProductException;
import com.exception.RatingException;
import com.exception.UserException;
import com.request.RatingRequest;
import com.response.HttpResponse;
import com.service.RatingService;
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
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(RatingController.class);

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createRating(@RequestBody RatingRequest req,
            @RequestHeader("Authorization") String jwt) {
        try {
            UserDTO user = userService.findUserProfileByJwt(jwt);
            RatingDTO rating = ratingService.createRating(req, user);
            logger.debug("Rating created: {}", rating);
            return ResponseEntity.status(HttpStatus.CREATED).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("rating", rating))
                    .message("Rating created successfully")
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .build());
        } catch (UserException | ProductException e) {
            logger.error("Error creating rating", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build());
        } catch (Exception e) {
            logger.error("Unexpected error during rating creation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .message("Unexpected error during rating creation")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<HttpResponse> getProductsRating(@PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) {
        try {
            // User user=userService.findUserProfileByJwt(jwt);
            List<RatingDTO> ratings = ratingService.getAllRatings(productId);
            logger.debug("Ratings fetched for product {}: {}", productId, ratings);
            return ResponseEntity.ok(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("ratings", ratings))
                    .message("Ratings fetched successfully")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .build());
        } catch (RatingException e) {
            logger.error("Error fetching ratings for product {}", productId, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build());
        } catch (Exception e) {
            logger.error("Unexpected error fetching ratings for product {}", productId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpResponse.builder()
                    .timeStamp(LocalDateTime.now().toString())
                    .message("Unexpected error fetching ratings")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }
}
