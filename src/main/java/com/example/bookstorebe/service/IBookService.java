package com.example.bookstorebe.service;

import com.example.bookstorebe.dto.BookDto;
import com.example.bookstorebe.dto.web.BookWebDto;
import com.example.bookstorebe.models.entity.Book;
import java.util.List;

/**
 * Service class for managing books.
 */
public interface IBookService {

  /**
   * Retrieves all books from the book repository.
   *
   * @return The list of books.
   */
  List<BookDto> getAllBooks();

  /**
   * Retrieves a single book by its ID.
   *
   * @param id The ID of the book to retrieve.
   *           //   * @param userDetails The authenticated user details.
   * @return The response entity containing the book information.
   */
  BookDto getOneBook(Long id);

  /**
   * Retrieves the list of favorite books for a given user.
   *
   * @param userId The ID of the user.
   * @return the list of favorite books
   */
  List<BookDto> getFavorites(Long userId);

  BookDto toDto(Book book, boolean attachList);

  BookWebDto toWebDto(BookDto dto);

  Book toEntity(BookDto book);

}
