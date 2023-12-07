package com.example.bookstorebe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for creating a comment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
  private String text;
  private Long bookId;
}
