package com.example.bookstorebe.dto.response;

import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.User;
import jakarta.persistence.Transient;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity comment for response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

  @Transient
  private Integer commentId;
  private String text;
  private Date date;
  private User user;
  private Book book;

}
