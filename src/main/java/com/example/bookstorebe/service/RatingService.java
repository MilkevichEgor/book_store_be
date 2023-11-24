package com.example.bookstorebe.service;

import com.example.bookstorebe.dto.RatingDto;
import com.example.bookstorebe.models.entity.Rating;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

  public RatingDto toDto(Rating rating) {
    return new RatingDto(
            rating.getId(),
            rating.getRatings()
    );
  }
}
