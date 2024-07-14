package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.ReviewDTO;
import com.model.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "userId", source = "user.userId")
    ReviewDTO toReviewDTO(Review review);

    // @Mapping(target="id", ignore=true)
    @Mapping(target = "product.productId", source = "productId")
    @Mapping(target = "user.userId", source = "userId")
    Review toReview(ReviewDTO reviewDto);
}
