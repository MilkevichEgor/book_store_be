package com.example.bookstorebe.service;

import com.example.bookstorebe.DTO.BookResponse;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.Comment;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.CommentRepository;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class CommentService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final SocketService socketService;

    @Autowired
    public CommentService(BookRepository bookRepository,
                          CommentRepository commentRepository,
                          UserRepository userRepository,
                          SocketService socketService) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.socketService = socketService;
    }

    public ResponseEntity<Comment> addComment(@RequestBody BookResponse reqBody, Authentication authentication) {
        try {

            User user = userRepository.findById(((UserDetailsImpl) authentication.getPrincipal()).getUser().getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID not found"));

            Book book = bookRepository.findById(reqBody.getBookId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this ID not found"));


            Comment comment = new Comment();
            comment.setBook(book);
            comment.setUser(user);
            comment.setText(reqBody.getText());
            comment.setDate(new Date());

            commentRepository.save(comment);
            socketService.broadcast("add comment");

            return ResponseEntity.status(HttpStatus.OK).body(comment);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
