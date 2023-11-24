package com.example.bookstorebe.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity genre.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "genre_id")
  @JsonProperty("genreId")
  private Long genreId;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(name = "book_genres_genre",
          joinColumns = @JoinColumn(name = "genre_id"),
          inverseJoinColumns = @JoinColumn(name = "book_id"))
  private List<Book> books;

}
