package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.ReviewDTO;
import com.model.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {   


    ReviewDTO toReviewDTO(Review review);
    
    // @Mapping(target="id", ignore=true)
    @Mapping(target="product", ignore=true)
    @Mapping(target="user", ignore=true)
    @Mapping(target="createdAt", ignore=true)
    Review toReview(ReviewDTO reviewDto);

    //TODO: Add Annotations
}
