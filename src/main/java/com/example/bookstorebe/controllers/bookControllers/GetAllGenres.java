package com.example.bookstorebe.controllers.bookControllers;

import com.example.bookstorebe.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/book")
public class GetAllGenres {

    private final GenresService genresService;
    @Autowired
    public GetAllGenres(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping(path= "/genres")
    public ResponseEntity<Map<String, Object>> getAllGenres() {
        return genresService.getAllGenres();
    }
}
