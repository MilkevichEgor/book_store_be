package com.example.bookstorebe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a data transfer object for a rating.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

  private Long id;
  private Long rating;

}
