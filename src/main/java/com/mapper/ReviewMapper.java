package com.mapper;

import org.mapstruct.Mapper;
import com.dto.ReviewDTO;
import com.model.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {    

    ReviewDTO toReviewDTO(Review review);
    
    Review toReview(ReviewDTO reviewDto);

    //TODO: Add Annotations
}
