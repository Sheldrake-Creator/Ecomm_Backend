package com.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.ProductDTO;
import com.dto.RatingDTO;
import com.dto.UserDTO;
import com.exception.CartItemException;
import com.exception.ProductException;
import com.exception.RatingException;
import com.mapper.RatingMapper;
import com.model.Rating;
import com.repository.RatingRepository;
import com.request.RatingRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ProductService productService;
    private final RatingMapper ratingMapper;
    private final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

    @Override
    public RatingDTO createRating(RatingRequest req, UserDTO user) throws ProductException {
        logger.debug("req: {}", req);
        logger.debug("userDTO in Request: {}", user);

        ProductDTO product = productService.findProductById(req.getProductId());
        logger.debug("Product Id: {}", product.getProductId());

        RatingDTO rating = new RatingDTO();
        rating.setRating(req.getRating());
        rating.setProductId(product.getProductId());
        rating.setCreatedAt(LocalDateTime.now());
        rating.setUserId(user.getUserId());
        Rating ratingEntity = ratingMapper.toRating(rating);
        System.out.println(" ");
        logger.debug("User: {}", user);
        logger.debug("UserID: {}", user.getUserId());

        System.out.println(" ");
        logger.debug("Product: {}", ratingEntity.getProduct());
        logger.debug("ProductId: {}", ratingEntity.getProduct().getProductId());

        Rating newRating = this.ratingRepository.save(ratingMapper.toRating(rating));

        return ratingMapper.toRatingDTO(newRating);
    }

    @Override
    public List<RatingDTO> getAllRatings(Long productId) throws RatingException {
        try {
            return ratingRepository.getAllProductsRating(productId).orElseThrow(() -> {
                logger.error("No Cart Found with UserId: {}", productId);
                return new CartItemException("No Cart Found with UserId: " + productId);
            }).stream().map(ratingMapper::toRatingDTO).collect(Collectors.toList());
        } catch (CartItemException e) {
            throw new RatingException(
                    "An unexpected error occurred while retrieving the rating for user with ProductId: ", e);
        }
    }
}
