package com.example.bookstorebe.service.impl;

import com.example.bookstorebe.dto.BookDto;
import com.example.bookstorebe.dto.CommentDto;
import com.example.bookstorebe.dto.GenreDto;
import com.example.bookstorebe.dto.RatingDto;
import com.example.bookstorebe.dto.UserDto;
import com.example.bookstorebe.dto.web.BookWebDto;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.service.IBookService;
import com.example.bookstorebe.service.ICommentService;
import com.example.bookstorebe.service.IGenresService;
import com.example.bookstorebe.service.IUserService;
import com.example.bookstorebe.service.RatingService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

/**
 * Service class for managing books.
 */
@EnableCaching
@Service
public class BookService implements IBookService {

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private IUserService userService;
  @Autowired
  private IGenresService genresService;
  @Autowired
  private RatingService ratingService;
  @Autowired
  private ICommentService commentService;
  
  /**
   * Retrieves all books from the book repository.
   *
   * @return The list of books.
   */
  @Cacheable("books")
  public List<BookDto> getAllBooks() {
    return toDto(bookRepository.findAll(), true);
  }

  /**
   * Retrieves a single book by its ID.
   *
   * @param id The ID of the book to retrieve.
   *           //   * @param userDetails The authenticated user details.
   * @return The response entity containing the book information.
   */
  @Cacheable("book")
  public BookDto getOneBook(Long id) {
    Optional<Book> book = bookRepository.findById(id);
    return toDto(book.orElse(null), true);
  }

  /**
   * Retrieves the list of favorite books for a given user.
   *
   * @param userId The ID of the user.
   * @return the list of favorite books
   */
  public List<BookDto> getFavorites(Long userId) {
    User user = userRepository.findById(userId).get();
    return toDto(user.getFavorites(), false);

  }

  /**
   * Converts a Book object to a BookDto object.
   *
   * @param book       The Book object to convert.
   * @param attachList Flag indicating whether to attach the lists of associated objects.
   * @return The converted BookDto object.
   */
  public BookDto toDto(Book book, boolean attachList) {
    if (book == null) {
      return null;
    }
    List<UserDto> users = new ArrayList<>();
    List<GenreDto> genres = new ArrayList<>();
    List<RatingDto> ratings = new ArrayList<>();
    List<CommentDto> comments = new ArrayList<>();
    if (attachList) {
      book.getUsers().forEach(user -> users.add(userService.toDto(user, attachList)));
      book.getGenres().forEach(genre -> genres.add(genresService.toDto(genre)));
      book.getRatings().forEach(rating -> ratings.add(ratingService.toDto(rating, attachList)));
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

  /**
   * Converts a collection of Book objects to a list of BookDto objects.
   *
   * @param books      the collection of Book objects to convert
   * @param attachList flag indicating whether to attach the lists of associated objects
   * @return a list of BookDto objects
   */
  public List<BookDto> toDto(Collection<Book> books, boolean attachList) {
    List<BookDto> bookDto = new ArrayList<>();
    books.forEach(book -> bookDto.add(toDto(book, attachList)));
    return bookDto;
  }

  @Override
  public BookWebDto toWebDto(BookDto dto) {
    return new BookWebDto(dto);
  }

  @Override
  public Book toEntity(BookDto book) {
    return null;
  }
}
