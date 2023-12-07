package com.example.bookstorebe.service;

import com.example.bookstorebe.dto.CommentDto;
import com.example.bookstorebe.models.entity.Comment;

/**
 * This service class handles operations related to comments on books.
 */
public interface ICommentService {
  CommentDto add(CommentDto request) throws Exception;

  CommentDto toDto(Comment comment);

  Comment toEntity(CommentDto commentDto);
}
