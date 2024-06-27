package com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.RatingDTO;
import com.model.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    RatingDTO toRatingDTO(Rating rating);

    Rating toRating(RatingDTO ratingDTO);  
}
