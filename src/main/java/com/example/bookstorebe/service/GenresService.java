package com.example.bookstorebe.service;

import com.example.bookstorebe.dto.GenreDto;
import com.example.bookstorebe.models.entity.Genre;
import com.example.bookstorebe.repository.GenreRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class for managing genres.
 */
@Service
public class GenresService implements IGenresService {

  private final GenreRepository genreRepository;

  public GenresService(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  /**
   * Retrieves all genres from the genre repository.
   *
   * @return A ResponseEntity containing a map of genres and an HTTP status code.
   */
  public List<GenreDto> getAllGenres() {

    return toDto(genreRepository.findAll());
  }

  public GenreDto toDto(Genre genre) {
    return new GenreDto(
            genre.getGenreId(),
            genre.getName()
    );
  }

  public List<GenreDto> toDto(List<Genre> genres) {
    List<GenreDto> dtos = new ArrayList<>();
    genres.forEach(genre -> dtos.add(toDto(genre)));
    return dtos;
  }
}
