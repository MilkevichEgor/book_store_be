package com.example.bookstorebe.dto;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

  private Long bookId;
  private String title;
  private String author;
  private Integer price;
  private String cover;
  private Date dateOfIssue;
  private Integer inStock;
  private String description;
  private Float averageRate;
  private List<UserDto> users;
  private List<GenreDto> genres;
  private List<RatingDto> ratings;
  private List<CommentDto> comments;
}
