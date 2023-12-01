package com.example.bookstorebe.dto.web;

import com.example.bookstorebe.dto.BookDto;
import com.example.bookstorebe.dto.CommentDto;
import com.example.bookstorebe.dto.RatingDto;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a data transfer object for front-end.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookWebDto {

  private Long bookId;
  private String title;
  private String author;
  private Integer price;
  private String cover;
  private Date dateOfIssue;
  private Integer inStock;
  private String description;
  private Float averageRate;
  private List<RatingDto> ratings;
  private List<CommentDto> comments;
  private Boolean isInFavorite;

  /**
   * Builds a BookWebDto object from a BookDto object.
   */
  public BookWebDto(BookDto dto, Long userId) {
    this.bookId = dto.getBookId();
    this.title = dto.getTitle();
    this.author = dto.getAuthor();
    this.price = dto.getPrice();
    this.cover = dto.getCover();
    this.dateOfIssue = dto.getDateOfIssue();
    this.inStock = dto.getInStock();
    this.description = dto.getDescription();
    this.averageRate = dto.getAverageRate();
    this.ratings = dto.getRatings();
    this.comments = dto.getComments();
    this.isInFavorite = dto.getUsers().stream().anyMatch(u -> u.getId().equals(userId));
  }

  /**
   * Builds a BookWebDto object from a BookDto object.
   *
   * @param dto DTO object.
   */
  public BookWebDto(BookDto dto) {
    this.bookId = dto.getBookId();
    this.title = dto.getTitle();
    this.author = dto.getAuthor();
    this.price = dto.getPrice();
    this.cover = dto.getCover();
    this.dateOfIssue = dto.getDateOfIssue();
    this.inStock = dto.getInStock();
    this.description = dto.getDescription();
    this.averageRate = dto.getAverageRate();
    this.ratings = dto.getRatings();
    this.comments = dto.getComments();
  }
}
