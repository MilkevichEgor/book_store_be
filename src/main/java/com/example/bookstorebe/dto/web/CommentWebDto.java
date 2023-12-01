package com.example.bookstorebe.dto.web;

import com.example.bookstorebe.dto.CommentDto;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a data transfer object for a comment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentWebDto {

  private Date date;
  private String text;

  public CommentWebDto(CommentDto commentDto) {
    this.date = commentDto.getDate();
    this.text = commentDto.getText();
  }
}
