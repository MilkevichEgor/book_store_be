package com.example.bookstorebe.controllers;

import com.example.bookstorebe.dto.BookDto;
import com.example.bookstorebe.dto.CommentDto;
import com.example.bookstorebe.dto.UserDto;
import com.example.bookstorebe.dto.request.BookFavoriteRequest;
import com.example.bookstorebe.dto.response.BookResponse;
import com.example.bookstorebe.dto.response.GenreResponse;
import com.example.bookstorebe.dto.web.BookWebDto;
import com.example.bookstorebe.service.CommentService;
import com.example.bookstorebe.service.GenresService;
import com.example.bookstorebe.service.IUserService;
import com.example.bookstorebe.service.impl.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for adding a comment to a book.
 */
@RestController
@RequestMapping("/book")
public class BookRestController {

  private final CommentService commentService;
  private final BookService bookService;
  @Autowired
  private IUserService userService;
  @Autowired
  private GenresService genresService;

  @Autowired
  public BookRestController(CommentService commentService,
                            BookService bookService) {
    this.commentService = commentService;
    this.bookService = bookService;
  }

  /**
   * Adds a comment to a book response.
   *
   * @param request        the book response to add the comment to
   * @param authentication the authentication object containing the user's credentials
   * @return the response entity containing the added comment
   */
  @PostMapping("/add-comment")
  public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto request,
                                               Authentication authentication) {
    request.setUserId(((UserDto) authentication.getPrincipal()).getId());
    CommentDto result;

    try {
      result = commentService.add(request);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }
    return ResponseEntity.ok(result);
  }

  @PostMapping("/add-favorites")
  public ResponseEntity<UserDto> addToFavorites(@RequestBody BookFavoriteRequest request,
                                                Authentication authentication) {

    Long user = ((UserDto) authentication.getPrincipal()).getId();
    UserDto result;

    if (request.getBookId() == null) {
      return ResponseEntity.badRequest().body(null);
    }

    try {
      result = userService.addToFavorites(request.getBookId(), user);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(null);
    }
    return ResponseEntity.ok(result);
  }

  /**
   * Remove a book from the user's favorites.
   *
   * @param authentication the authentication object for the current user
   * @return a ResponseEntity containing a UserResponse object
   */
  @DeleteMapping("/remove-favorites")
  public ResponseEntity<UserDto> removeFromFavorites(@RequestBody BookFavoriteRequest request,
                                                     Authentication authentication) {
    Long user = ((UserDto) authentication.getPrincipal()).getId();
    UserDto result;

    if (request.getBookId() == null) {
      return ResponseEntity.badRequest().body(null);
    }

    try {
      result = userService.removeFromFavorites(request.getBookId(), user);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(null);
    }
    return ResponseEntity.ok(result);
  }

  /**
   * Retrieve all books.
   *
   * @return A ResponseEntity containing a map of the books.
   */
  @GetMapping(path = "/all")
  public ResponseEntity<List<BookDto>> getAllBooks() {

    return ResponseEntity.ok(bookService.getAllBooks());
  }

  /**
   * Retrieves the list of favorite books for the authenticated user.
   *
   * @param authentication the authentication token for the user
   * @return the list of favorite books
   */
  @GetMapping(path = "/favorites")
  public ResponseEntity<List<BookDto>> getFavorites(Authentication authentication) {
    Long user = ((UserDto) authentication.getPrincipal()).getId();
    List<BookDto> result;

    try {
      result = bookService.getFavorites(user);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }
    return ResponseEntity.ok(result);
  }

  /**
   * Retrieves a single book by its ID.
   *
   * @param id             The ID of the book to retrieve.
   * @param authentication the authentication token for the user
   * @return The response entity containing the book information.
   */
  @GetMapping("/{id}")
  public ResponseEntity<BookResponse> getOneBook(@PathVariable Long id,
                                                 Authentication authentication) {
    UserDto user = (UserDto) authentication.getPrincipal();
    BookResponse result = new BookResponse();

    try {
      BookDto dto = bookService.getOneBook(id);
      result.setBook(new BookWebDto(dto, user.getId()));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }

    return ResponseEntity.ok(result);
  }

  /**
   * Retrieves all genres.
   *
   * @return the response entity containing a map of genres
   */
  @GetMapping(path = "/genres")
  public ResponseEntity<GenreResponse> getAllGenres() {
    return ResponseEntity.ok(new GenreResponse(genresService.getAllGenres()));
  }
}
