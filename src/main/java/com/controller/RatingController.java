package com.controller;

import com.dto.RatingDTO;
import com.dto.UserDTO;
import com.exception.ProductException;
import com.exception.UserException;
import com.request.RatingRequest;
import com.service.RatingService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingRequest req,
                                               @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        UserDTO user = userService.findUserProfileByJwt(jwt);
        RatingDTO rating = ratingService.createRating(req, user);
        return new ResponseEntity<RatingDTO>(rating, HttpStatus.CREATED);

    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<RatingDTO>> getProductsRating(@PathVariable Long productId,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        // User user=userService.findUserProfileByJwt(jwt);
        List<RatingDTO> ratings=ratingService.getAllRatings(productId);
        return new ResponseEntity<List<RatingDTO>>(ratings,HttpStatus.CREATED);
    }
}
