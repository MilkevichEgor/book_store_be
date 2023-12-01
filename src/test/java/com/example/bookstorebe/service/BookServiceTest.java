package com.example.bookstorebe.service;

import com.example.bookstorebe.constant.UserRole;
import com.example.bookstorebe.dto.BookDto;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {IBookService.class, BookRepository.class, UserRepository.class})
@ComponentScan(basePackages = {"com.example.bookstorebe"})
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableConfigurationProperties
@ActiveProfiles("test")
class BookServiceTest {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private IBookService bookService;


  void setup() {
    Book book1 = new Book();
    book1.setTitle("Book 1");
    book1.setAuthor("Author 1");
    book1.setPrice(10);
    book1.setCover("Cover 1");
    book1.setDateOfIssue(null);
    book1.setInStock(5);
    book1.setDescription("Description 1");
    book1.setAverageRate(4.5f);
    book1.setComments(List.of());
    book1.setGenres(List.of());
    book1.setRatings(List.of());
    book1.setUsers(Set.of());

    Book book2 = new Book();
    book2.setTitle("Book 2");
    book2.setAuthor("Author 2");
    book2.setPrice(20);
    book2.setCover("Cover 2");
    book2.setDateOfIssue(null);
    book2.setInStock(10);
    book2.setDescription("Description 2");
    book2.setAverageRate(3.5f);
    book2.setComments(List.of());
    book2.setGenres(List.of());
    book2.setRatings(List.of());
    book2.setUsers(Set.of());

    User user = new User();
    user.setRole(UserRole.ROLE_USER);
    user.setName("User 1");
    user.setEmail("Email 2");
    user.setPassword("Password 1");
    user.setAvatar("Avatar 1");
    user.setComments(List.of());
    user.setRatings(List.of());
    user.setFavorites(new HashSet<>());

    User user2 = new User();
    user2.setRole(UserRole.ROLE_USER);
    user2.setName("User 2");
    user2.setEmail("Email 2");
    user2.setPassword("Password 2");
    user2.setAvatar("Avatar 2");
    user2.setComments(List.of());
    user2.setRatings(List.of());
    user2.setFavorites(new HashSet<>());

    List<Book> books = bookRepository.saveAll(List.of(book1, book2));
    user = userRepository.save(user);
    user.setFavorites(new HashSet<>(books));
    userRepository.save(user);

    Assertions.assertEquals(user.getUserId(), 1L);
    Assertions.assertEquals(user.getFavorites().size(), 2);
  }

  @Test
  void getOneBookTest() {
    Long id = 1L;
    BookDto bookDto = bookService.getOneBook(id);
    Assertions.assertEquals(id, bookDto.getBookId());
  }

  @Test
  void getOneBookEmptyTest() {
    Long id = 3L;
    BookDto bookDto = bookService.getOneBook(id);
    Assertions.assertNull(bookDto);
  }

  @Test
  void getAllBooksTest() {
    List<BookDto> books = bookService.getAllBooks();
    Assertions.assertEquals(2, books.size());
  }

  @Test
  void getAllBooksEmptyTest() {
    List<BookDto> books = bookService.getAllBooks();
    Assertions.assertEquals(0, books.size());
  }

  @Test
  void getFavoritesTest() {
    setup();
    Long userId = 1L;
    List<BookDto> books = bookService.getFavorites(userId);
    Assertions.assertEquals(2, books.size());
  }

  @Test
  void getFavoritesEmptyTest() {
    Long userId = 2L;
    List<BookDto> books = bookService.getFavorites(userId);
    Assertions.assertEquals(0, books.size());
  }

}
