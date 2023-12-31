package com.example.bookstorebe.service;

import com.example.bookstorebe.constant.UserRole;
import com.example.bookstorebe.dto.BookDto;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
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


  @BeforeEach
  void setup() {

    User user1 = createUser("Name 1", "Email 1", "Password 1");
    User user2 = createUser("Name 2", "Email 2", "Password 2");
    Book book1 = createBook("Book 1", "Author 1", 30);
    Book book2 = createBook("Book 2", "Author 2", 40);

    List<Book> books = bookRepository.saveAll(List.of(book1, book2));
    user1 = userRepository.save(user1);
    user1.setFavorites(new HashSet<>(books));
    userRepository.save(user1);
    userRepository.save(user2);

  }

  private static Book createBook(String title, String author, int price) {
    Book book = new Book();
    book.setTitle(title);
    book.setAuthor(author);
    book.setPrice(price);
    book.setCover("Cover");
    book.setDateOfIssue(null);
    book.setInStock(5);
    book.setDescription("Description");
    book.setAverageRate(4.5f);
    book.setComments(List.of());
    book.setGenres(List.of());
    book.setRatings(List.of());
    book.setUsers(Set.of());
    book.setComments(List.of());
    return book;
  }

  private static User createUser(String name, String email, String password) {
    User user = new User();
    user.setRole(UserRole.ROLE_USER);
    user.setName(name);
    user.setEmail(email);
    user.setPassword(password);
    user.setAvatar(new Random().nextLong() + ".jpg");
    user.setComments(List.of());
    user.setRatings(List.of());
    user.setFavorites(new HashSet<>());
    return user;
  }

  @Test
  @DirtiesContext
  void getOneBookTest() {
    Long id = 1L;
    BookDto bookDto = bookService.getOneBook(id);
    Assertions.assertEquals(id, bookDto.getBookId());
  }

  @Test
  @DirtiesContext
  void getOneBookEmptyTest() {
    Long id = 5L;
    BookDto bookDto = bookService.getOneBook(id);
    Assertions.assertNull(bookDto);
  }

  @Test
  @DirtiesContext
  void getAllBooksTest() {
    List<BookDto> books = bookService.getAllBooks();
    Assertions.assertEquals(2, books.size());
  }

  @Test
  @DirtiesContext
  void getFavoritesTest() {
    Long userId = 1L;
    List<BookDto> books = bookService.getFavorites(userId);
    Assertions.assertEquals(2, books.size());

  }

  @Test
  @DirtiesContext
  void getFavoritesEmptyTest() {
    Long userId = 2L;
    List<BookDto> books = bookService.getFavorites(userId);
    Assertions.assertEquals(0, books.size());
  }

  @Test
  @DirtiesContext
  void toDtoTest() {
    Long id = 1L;
    Book book = bookRepository.findById(id).get();

    BookDto bookDto = bookService.toDto(book, true);

    Assertions.assertEquals(id, bookDto.getBookId());

    Assertions.assertEquals("Book 1", bookDto.getTitle());
    Assertions.assertEquals("Author 1", bookDto.getAuthor());
    Assertions.assertEquals(30, bookDto.getPrice());
    Assertions.assertEquals(bookDto.getUsers().size(), 1);
    Assertions.assertEquals(bookDto.getComments().size(), 0);
    Assertions.assertEquals(bookDto.getRatings().size(), 0);
    Assertions.assertEquals(bookDto.getGenres().size(), 0);
  }


}
