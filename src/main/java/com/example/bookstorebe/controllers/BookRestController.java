package com.example.bookstorebe.controllers;

import com.example.bookstorebe.dto.BookDto;
import com.example.bookstorebe.dto.CommentDto;
import com.example.bookstorebe.dto.GenreDto;
import com.example.bookstorebe.dto.UserDto;
import com.example.bookstorebe.dto.request.BookFavoriteRequest;
import com.example.bookstorebe.dto.request.CommentRequest;
import com.example.bookstorebe.dto.response.OneFieldResponse;
import com.example.bookstorebe.dto.web.BookWebDto;
import com.example.bookstorebe.dto.web.CommentWebDto;
import com.example.bookstorebe.service.CommentService;
import com.example.bookstorebe.service.GenresService;
import com.example.bookstorebe.service.IBookService;
import com.example.bookstorebe.service.IUserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookRestController {

  private final Logger logger = LoggerFactory.getLogger(BookRestController.class);

  private final CommentService commentService;
  @Autowired
  private IBookService bookService;
  @Autowired
  private IUserService userService;
  @Autowired
  private GenresService genresService;

  @Autowired
  public BookRestController(CommentService commentService) {
    this.commentService = commentService;
  }

  /**
   * Adds a comment to a book response.
   *
   * @param request        the book response to add the comment to
   * @param authentication the authentication object containing the user's credentials
   * @return the response entity containing the added comment
   */
  @PostMapping("/add-comment")
  public ResponseEntity<CommentWebDto> addComment(@RequestBody CommentRequest request,
                                                  Authentication authentication) {
    Long userId = (((UserDto) authentication.getPrincipal()).getId());

    CommentDto commentDto = new CommentDto(null, new Date(), request.getText(), userId, request.getBookId());

    if (request.getBookId() == null) {
      return ResponseEntity.badRequest().body(null);
    }
    try {
      commentService.add(commentDto);
      return ResponseEntity.ok(new CommentWebDto(commentDto));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(null);
    }
  }

  /**
   * Adds a book to the user's favorites.
   *
   * @param request        The request object containing the book ID.
   * @param authentication The authentication object for the current user.
   * @return The response entity containing the updated user information.
   */
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
      logger.error("Add to favorites API failed", e);
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
  public ResponseEntity<Map<String, List<BookWebDto>>> getAllBooks() {
    List<BookWebDto> result = new ArrayList<>();

    List<BookDto> books = bookService.getAllBooks();
    for (BookDto dto : books) {
      result.add(bookService.toWebDto(dto));
    }

    return ResponseEntity.ok(OneFieldResponse.of("books", result));
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
  public ResponseEntity<Map<String, BookWebDto>> getOneBook(@PathVariable Long id,
                                                            Authentication authentication) {
    UserDto user = (UserDto) authentication.getPrincipal();
    BookDto dto;
    try {
      dto = bookService.getOneBook(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }

    return ResponseEntity.ok(OneFieldResponse.of("book", new BookWebDto(dto, user.getId())));
  }

  /**
   * Retrieves all genres.
   *
   * @return the response entity containing a map of genres
   */
  @GetMapping(path = "/genres")
  public ResponseEntity<Map<String, List<GenreDto>>> getAllGenres() {
    return ResponseEntity.ok(OneFieldResponse.of("genres", genresService.getAllGenres()));
  }
}
