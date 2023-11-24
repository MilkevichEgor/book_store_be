package com.example.bookstorebe.repository;

import com.example.bookstorebe.models.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for managing genres.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
