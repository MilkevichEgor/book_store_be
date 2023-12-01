package com.example.bookstorebe.service;

import com.example.bookstorebe.dto.CommentDto;
import com.example.bookstorebe.models.entity.Comment;

public interface ICommentService {
  CommentDto add(CommentDto request) throws Exception;
  
  CommentDto toDto(Comment comment);

  Comment toEntity(CommentDto commentDto);
}
