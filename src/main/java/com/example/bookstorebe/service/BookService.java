package com.example.bookstorebe.service;

import com.example.bookstorebe.DTO.BookResponse;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<Map<String, Object>> getAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        Map<String, Object> book = new HashMap<>();
        book.put("books", books);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

//    public ResponseEntity<Map<String, Object>> getAllBooks(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String title
//    ) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "title"));
//
//        Page<Book> bookPage = bookRepository.findAllByTitleContainingIgnoreCase(title, pageable);
//        Map<String, Object> bookPageMap = new HashMap<>();
//        bookPage.getContent().forEach(book -> bookPageMap.put("books", bookPage.getContent()));
////        bookPageMap.put("books", bookPage);
//        return new ResponseEntity<>(bookPageMap, HttpStatus.OK);
//    }

//    public Page<Book> getAllBooks(int page, int size, String title, List<String> genres, double priceFrom, double priceTo, String order, String orderDir) {
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(orderDir), order);
//
//        Specification<Book> specification = (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (title != null && !title.isEmpty()) {
//                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
//            }
//
//            if (genres != null && !genres.isEmpty()) {
//                Join<Book, Genre> genreJoin = root.join("genres");
//                predicates.add(genreJoin.get("genreName").in(genres));
//            }
//
//            predicates.add(criteriaBuilder.between(root.get("price"), priceFrom, priceTo));
//
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        };
//
//        return bookRepository.findAll(specification, pageable);
//    }


    public ResponseEntity<Map<String, BookResponse>> getOneBook(@PathVariable Integer id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Book book = bookRepository.getBookById(id);

            if (book == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            boolean isInFavorites = userDetails != null && book.getUsers().contains(userDetails.getUser());
            BookResponse bookResponse = book.toResponse(isInFavorites);
            Map<String, BookResponse> bookResponseList = new HashMap<>();
            bookResponseList.put("book",bookResponse);


            return ResponseEntity.ok(bookResponseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
