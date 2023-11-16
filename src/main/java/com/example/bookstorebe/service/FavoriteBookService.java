package com.example.bookstorebe.service;

import com.example.bookstorebe.DTO.BookResponse;
import com.example.bookstorebe.DTO.UserResponse;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FavoriteBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public FavoriteBookService(BookRepository bookRepository,
                               UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    public ResponseEntity<UserResponse> addToFavorites(@RequestBody BookResponse reqBook, Authentication authentication) {
        try {

            User user = userRepository.findById(((UserDetailsImpl) authentication.getPrincipal()).getUser().getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID not found"));

            Book book = bookRepository.findById(reqBook.getBookId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this ID not found"));

            if (!user.getFavorites().contains(book) ) {
                user.addToFavorites(book);

                userRepository.save(user);
            }

            UserResponse userResponse = user.toResponse();

            return ResponseEntity.ok(userResponse);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<UserResponse> removeFromFavorites(@RequestBody BookResponse reqBook, Authentication authentication) {
        try {

            User user = userRepository.findById(((UserDetailsImpl) authentication.getPrincipal()).getUser().getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID not found"));

            Book book = bookRepository.findById(reqBook.getBookId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this ID not found"));

            user.removeFromFavorites(book);
            userRepository.save(user);
            book.isInFavorite();

            UserResponse userResponse = user.toResponse();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public @ResponseBody Iterable<Book> getFavorites(Authentication authentication) {
        User user = userRepository.findById(((UserDetailsImpl) authentication.getPrincipal()).getUser().getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID not found"));

        return user.getFavorites();
    }
}

