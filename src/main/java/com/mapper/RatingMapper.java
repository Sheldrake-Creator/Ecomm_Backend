package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.RatingDTO;
import com.model.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(target = "ratingId", ignore = true)
    RatingDTO toRatingDTO(Rating rating);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    Rating toRating(RatingDTO ratingDTO);  
}
