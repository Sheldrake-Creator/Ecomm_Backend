package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.RatingDTO;
import com.model.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "userId", source = "user.userId")
    RatingDTO toRatingDTO(Rating rating);

    @Mapping(target = "product.productId", source = "productId")
    @Mapping(target = "user.userId", source = "userId")
    Rating toRating(RatingDTO ratingDTO);
}
