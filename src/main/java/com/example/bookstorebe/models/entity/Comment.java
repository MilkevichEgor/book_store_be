package com.example.bookstorebe.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity comment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"book", "user"})
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long commentId;

  @Column(nullable = false)
  private Date date;

  @Column(nullable = false)
  private String text;

  @ManyToOne
  @JoinColumn(name = "book_id")
  @JsonBackReference
  private Book book;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
