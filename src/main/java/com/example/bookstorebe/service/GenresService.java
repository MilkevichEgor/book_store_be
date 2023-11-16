package com.example.bookstorebe.service;

import com.example.bookstorebe.models.entity.Genre;
import com.example.bookstorebe.repository.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GenresService {

    private final GenreRepository genreRepository;

    public GenresService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public ResponseEntity<Map<String, Object>> getAllGenres() {
        Iterable<Genre> genres = genreRepository.findAll();
        Map<String, Object> genre = new HashMap<>();
        genre.put("genres", genres);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }
}
