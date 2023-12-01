package com.example.bookstorebe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a data transfer object for a genre.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

  private Long genreId;
  private String name;

}
