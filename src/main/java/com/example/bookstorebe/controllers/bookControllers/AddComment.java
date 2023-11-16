package com.example.bookstorebe.controllers.bookControllers;

import com.example.bookstorebe.DTO.BookResponse;
import com.example.bookstorebe.models.entity.Comment;
import com.example.bookstorebe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/book")
public class AddComment {

    private final CommentService commentService;
    @Autowired
    public AddComment(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add-comment")
    public ResponseEntity<Comment> addComment(@RequestBody BookResponse reqBody, Authentication authentication) {
        return commentService.addComment(reqBody, authentication);
    }
}
