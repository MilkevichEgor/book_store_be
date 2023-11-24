package com.example.bookstorebe.service.impl;

import com.example.bookstorebe.dto.BookDto;
import com.example.bookstorebe.dto.CommentDto;
import com.example.bookstorebe.dto.GenreDto;
import com.example.bookstorebe.dto.RatingDto;
import com.example.bookstorebe.dto.UserDto;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.service.CommentService;
import com.example.bookstorebe.service.GenresService;
import com.example.bookstorebe.service.IBookService;
import com.example.bookstorebe.service.IUserService;
import com.example.bookstorebe.service.RatingService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing books.
 */
@Service
public class BookService implements IBookService {

  private final BookRepository bookRepository;
  private final UserRepository userRepository;
  @Autowired
  private IUserService userService;
  private final GenresService genresService;
  private final RatingService ratingService;
  private final CommentService commentService;

  @Autowired
  public BookService(BookRepository bookRepository,
                     UserRepository userRepository,
                     GenresService genresService,
                     RatingService ratingService,
                     CommentService commentService) {
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
    this.genresService = genresService;
    this.ratingService = ratingService;
    this.commentService = commentService;
  }

  /**
   * Retrieves all books from the book repository.
   *
   * @return The list of books.
   */
  public List<BookDto> getAllBooks() {

    return toDto(bookRepository.findAll());
  }

  /**
   * Retrieves a single book by its ID.
   *
   * @param id The ID of the book to retrieve.
   *           //   * @param userDetails The authenticated user details.
   * @return The response entity containing the book information.
   */
  public BookDto getOneBook(Long id) {
    return toDto(bookRepository.findById(id).get(), true);
  }

  /**
   * Retrieves the list of favorite books for a given user.
   *
   * @param userId The ID of the user.
   * @return the list of favorite books
   */
  public List<BookDto> getFavorites(Long userId) {

    User user = userRepository.findById(userId).get();

    return toDto(user.getFavorites());
  }

  public BookDto toDto(Book book, boolean attachList) {
    List<UserDto> users = new ArrayList<>();
    List<GenreDto> genres = new ArrayList<>();
    List<RatingDto> ratings = new ArrayList<>();
    List<CommentDto> comments = new ArrayList<>();
    if (attachList) {
      book.getUsers().forEach(user -> users.add(userService.toDto(user, false)));
      book.getGenres().forEach(genre -> genres.add(genresService.toDto(genre)));
      book.getRatings().forEach(rating -> ratings.add(ratingService.toDto(rating)));
      book.getComments().forEach(comment -> comments.add(commentService.toDto(comment)));
    }
    return new BookDto(
            book.getBookId(),
            book.getTitle(),
            book.getAuthor(),
            book.getPrice(),
            book.getCover(),
            book.getDateOfIssue(),
            book.getInStock(),
            book.getDescription(),
            book.getAverageRate(),
            users,
            genres,
            ratings,
            comments
    );
  }

  public List<BookDto> toDto(Collection<Book> books) {
    List<BookDto> bookDto = new ArrayList<>();
    books.forEach(book -> bookDto.add(toDto(book, true)));
    return bookDto;
  }

  @Override
  public Book toEntity(BookDto book) {
    return null;
  }
}
