package com.example.bookstorebe.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

  private Long commentId;
  private Date date;
  private String text;
  private Long userId;
  private Long bookId;
}
