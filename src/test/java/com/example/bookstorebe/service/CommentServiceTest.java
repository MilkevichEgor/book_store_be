package com.example.bookstorebe.service;

import com.example.bookstorebe.constant.UserRole;
import com.example.bookstorebe.dto.CommentDto;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.Comment;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.CommentRepository;
import com.example.bookstorebe.repository.UserRepository;
import java.util.Date;
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

@SpringBootTest(classes = {ICommentService.class, BookRepository.class, UserRepository.class, CommentRepository.class})
@ComponentScan(basePackages = {"com.example.bookstorebe"})
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableConfigurationProperties
@ActiveProfiles("test")
public class CommentServiceTest {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private ICommentService commentService;

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

  private static CommentDto createComment(Long userId, Long bookId, String text) {
    CommentDto comment = new CommentDto();
    comment.setDate(new Date());
    comment.setText(text);
    comment.setUserId(userId);
    comment.setBookId(bookId);
    return comment;
  }

  @Test
  @DirtiesContext
  void addCommentTest() throws Exception {

    Long userId = 1L;
    Long bookId = 1L;
    String text = "Text";

    CommentDto request = createComment(userId, bookId, text);
    Comment comment = commentService.toEntity(request);

    commentRepository.save(comment);

    Assertions.assertEquals(userId, request.getUserId());
    Assertions.assertEquals(bookId, request.getBookId());
    Assertions.assertEquals("Text", request.getText());

  }

//  @Test
//  @DirtiesContext
//  void addCommentBadRequestTest() throws Exception {
//
//    Long userId = 1L;
//    Long bookId = 1L;
//    String text = "";
//
//    CommentDto request = createComment(userId, bookId, text);
//    Comment comment = commentService.toEntity(request);
//
//    commentRepository.save(comment);
//
//
//  }
}
