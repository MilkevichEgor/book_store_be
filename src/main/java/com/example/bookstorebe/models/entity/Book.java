package com.example.bookstorebe.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity book.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"users", "genres", "comments", "ratings"})
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String author;
  @Column(nullable = false)
  private Integer price;
  private String cover;
  private Date dateOfIssue;
  private Integer inStock;
  private String description;
  private Float averageRate;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "book_genres_genre",
          joinColumns = @JoinColumn(name = "book_id"),
          inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private List<Genre> genres;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "book_users_id",
          joinColumns = @JoinColumn(name = "book_id"),
          inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> users;

  @OneToMany(mappedBy = "book")
  @JsonManagedReference
  private List<Comment> comments;

  @OneToMany(mappedBy = "book")
  private List<Rating> ratings;
}
