package com.example.bookstorebe.controllers.bookControllers;

import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.service.BookService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/book")
public class GetAllBooks {

    private final BookService bookService;

    @Autowired
    public GetAllBooks(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Map<String, Object>> getAllBooks() {
        return bookService.getAllBooks();
    }

//    @GetMapping(path = "/all")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> getAllBooks(
//            @RequestParam(defaultValue = "0") @Min(0) int page,
//            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
//            @RequestParam(required = false) String title
//    ) {
//        return bookService.getAllBooks(page, size, title);
//    }

//    public ResponseEntity<Map<String, Object>> getAllBooks(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) List<String> genres,
//            @RequestParam(defaultValue = "0") double priceFrom,
//            @RequestParam(defaultValue = "1000") double priceTo,
//            @RequestParam(defaultValue = "id") String order,
//            @RequestParam(defaultValue = "asc") String orderDir
//    ) {
//        Page<Book> bookPage = bookService.getAllBooks(page, size, title, genres, priceFrom, priceTo, order, orderDir);
//        List<Book> books = bookPage.getContent();
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("books", books);
//        response.put("currentPage", bookPage.getNumber());
//        response.put("totalItems", bookPage.getTotalElements());
//        response.put("totalPages", bookPage.getTotalPages());
//
//        return ResponseEntity.ok(response);
//    }

}
