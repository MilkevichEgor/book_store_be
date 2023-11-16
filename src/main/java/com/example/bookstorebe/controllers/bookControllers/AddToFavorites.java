package com.example.bookstorebe.controllers.bookControllers;

import com.example.bookstorebe.DTO.BookResponse;
import com.example.bookstorebe.DTO.UserResponse;
import com.example.bookstorebe.service.FavoriteBookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class AddToFavorites {

    private final FavoriteBookService favoriteBookService;
    @Autowired
    public AddToFavorites(FavoriteBookService favoriteBookService) {
        this.favoriteBookService = favoriteBookService;
    }

    @Transactional
    @PostMapping("/add-favorites")
    public ResponseEntity<UserResponse> addToFavorites(@RequestBody BookResponse reqBook, Authentication authentication) {
        return favoriteBookService.addToFavorites(reqBook, authentication);
    }
}
