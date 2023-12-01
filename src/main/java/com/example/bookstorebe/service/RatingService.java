package com.example.bookstorebe.service;

import com.example.bookstorebe.dto.RatingDto;
import com.example.bookstorebe.models.entity.Rating;
import org.springframework.stereotype.Service;

/**
 * Service class for managing ratings.
 */
@Service
public class RatingService {

  /**
   * Convert a Rating object to a RatingDto object.
   *
   * @param rating The Rating object to convert
   * @return The converted RatingDto object
   */
  public RatingDto toDto(Rating rating, boolean attachList) {
    return new RatingDto(
            rating.getId(),
            rating.getRatings()
    );
  }
}
