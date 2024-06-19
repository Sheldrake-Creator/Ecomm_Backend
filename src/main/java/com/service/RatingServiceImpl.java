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
import com.exception.ProductException;
import com.mapper.RatingMapper;
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
        ProductDTO product = productService.findProductById(req.getProductId());

        RatingDTO rating = new RatingDTO();
        rating.setRating(req.getRating());
        rating.setProduct(product);
        rating.setCreatedAt(LocalDateTime.now());
        rating.setUser(user);
        this.ratingRepository.save(ratingMapper.toRating(rating));

        return rating;
    }

    @Override
    public List<RatingDTO> getAllRatings(Long productId) {
        return ratingRepository.getAllProductsRating(productId)
                .stream()
                .map(ratingMapper::toRatingDTO)
                .collect(Collectors.toList());
    }
}
