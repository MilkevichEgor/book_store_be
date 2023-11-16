package com.example.bookstorebe.controllers.bookControllers;

import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.service.FavoriteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/book")
public class GetFavorites {

    private final FavoriteBookService favoriteBookService;
    @Autowired
    public GetFavorites(FavoriteBookService favoriteBookService) {
        this.favoriteBookService = favoriteBookService;
    }

    @GetMapping(path = "/favorites")
    public @ResponseBody Iterable<Book> getFavorites(Authentication authentication) {
        return favoriteBookService.getFavorites(authentication);
    }
}
