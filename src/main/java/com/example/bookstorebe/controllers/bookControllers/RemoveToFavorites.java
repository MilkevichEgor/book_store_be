package com.example.bookstorebe.controllers.bookControllers;

import com.example.bookstorebe.DTO.BookResponse;
import com.example.bookstorebe.DTO.UserResponse;
import com.example.bookstorebe.service.FavoriteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class RemoveToFavorites {

    private final FavoriteBookService favoriteBookService;
    @Autowired
    public RemoveToFavorites(FavoriteBookService favoriteBookService) {
        this.favoriteBookService = favoriteBookService;
    }

    @Transactional
    @DeleteMapping("/remove-favorites")
    public ResponseEntity<UserResponse> removeFromFavorites(@RequestBody BookResponse reqBook, Authentication authentication) {
        return favoriteBookService.removeFromFavorites(reqBook, authentication);
    }
}
