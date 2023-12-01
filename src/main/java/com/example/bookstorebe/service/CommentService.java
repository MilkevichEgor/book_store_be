package com.example.bookstorebe.service;

import com.example.bookstorebe.dto.CommentDto;
import com.example.bookstorebe.models.entity.Comment;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.CommentRepository;
import com.example.bookstorebe.repository.UserRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service class handles operations related to comments on books.
 */
@Service
public class CommentService implements ICommentService {

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SocketService socketService;

  /**
   * Adds a comment to a book.
   *
   * @return a ResponseEntity containing the added comment
   */
  @Override
  public CommentDto add(CommentDto request) throws Exception {
    Comment comment = toEntity(request);
    comment.setDate(new Date());
    Comment result = commentRepository.save(comment);

    socketService.broadcast(request.getText(), "add comment");

    return toDto(result);
  }

  /**
   * Converts a Comment object to a CommentDto object.
   *
   * @param comment The Comment object to convert.
   * @return The converted CommentDto object.
   */
  @Override
  public CommentDto toDto(Comment comment) {
    return new CommentDto(
            comment.getCommentId(),
            comment.getDate(),
            comment.getText(),
            comment.getBook().getBookId(),
            comment.getUser().getUserId()
    );
  }

  @Override
  public Comment toEntity(CommentDto commentDto) {
    return new Comment(
            commentDto.getCommentId(),
            commentDto.getDate(),
            commentDto.getText(),
            bookRepository.findById(commentDto.getBookId()).get(),
            userRepository.findById(commentDto.getUserId()).get()
    );
  }
}
