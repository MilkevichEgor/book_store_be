package com.example.bookstorebe.service;

import com.example.bookstorebe.dto.GenreDto;
import com.example.bookstorebe.models.entity.Genre;
import java.util.List;

public interface IGenresService {

  /**
   * Retrieves all genres from the genre repository.
   *
   * @return A ResponseEntity containing a map of genres and an HTTP status code.
   */
  List<GenreDto> getAllGenres();

  GenreDto toDto(Genre genre);
}
