package com.example.bookstorebe.controllers.bookControllers;

import com.example.bookstorebe.DTO.BookResponse;
import com.example.bookstorebe.security.UserDetailsImpl;
import com.example.bookstorebe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/book")
public class GetOneBook {

    private final BookService bookService;
    @Autowired
    public GetOneBook(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, BookResponse>> getOneBook(@PathVariable Integer id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return bookService.getOneBook(id, userDetails);
    }
}
