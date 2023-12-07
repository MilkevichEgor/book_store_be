package com.example.bookstorebe.service;

import com.example.bookstorebe.constant.UserRole;
import com.example.bookstorebe.dto.UserDto;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {IUserService.class, BookRepository.class, UserRepository.class})
@ComponentScan(basePackages = {"com.example.bookstorebe"})
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableConfigurationProperties
@ActiveProfiles("test")
public class UserServiceTest {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private IUserService userService;

  @Autowired
  private PasswordEncoder encoder;

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
  void saveUserTest() {

    UserDto userDto = new UserDto();
    userDto.setEmail("test@example.com");
    userDto.setName("test");
    userDto.setPassword("test");
    userDto.setRole(UserRole.ROLE_USER);

    UserDto savedUser = userService.save(userDto);

    Assertions.assertEquals("test@example.com", savedUser.getEmail());
    Assertions.assertEquals(UserRole.ROLE_USER, savedUser.getRole());
    Assertions.assertTrue(encoder.matches("test", savedUser.getPassword()));
  }

  @Test
  @DirtiesContext
  void getCurrentUserTest() {

    Long userId = 1L;
    UserDto userDto = userService.getCurrentUser(userId);
    Assertions.assertEquals("Email 1", userDto.getEmail());
    Assertions.assertEquals("Name 1", userDto.getName());
    Assertions.assertEquals(UserRole.ROLE_USER, userDto.getRole());
  }

  @Test
  @DirtiesContext
  void updateAvatarTest() {
    Long userId = 1L;

    MockMultipartFile avatar = new MockMultipartFile(
            "file", "test.png", MediaType.MULTIPART_FORM_DATA_VALUE, "test".getBytes());

    UserDto userDto = userService.updateAvatar(avatar, userId);

  }

  @Test
  @DirtiesContext
  void addFavoriteTest() {

    Long userId = 2L;
    Long bookId = 1L;

    UserDto userDto = userService.addToFavorites(bookId, userId);

    Assertions.assertEquals(1, userDto.getFavorites().size());
    Assertions.assertEquals("Book 1", userDto.getFavorites().get(0).getTitle());
  }

  @Test
  @DirtiesContext
  void removeFavoriteTest() throws Exception {

    Long userId = 1L;
    Long bookId = 1L;

    UserDto userDto = userService.removeFromFavorites(bookId, userId);

    Assertions.assertEquals(1, userDto.getFavorites().size());

  }

  @Test
  @DirtiesContext
  void toDtoTest() {

    User user = userRepository.findById(1L).get();
    UserDto userDto = userService.toDto(user, true);

    Assertions.assertEquals("Email 1", userDto.getEmail());
    Assertions.assertEquals("Name 1", userDto.getName());
    Assertions.assertEquals(UserRole.ROLE_USER, userDto.getRole());
    Assertions.assertEquals(2, userDto.getFavorites().size());
  }

  @Test
  @DirtiesContext
  void findByEmailTest() {
    String email = "Email 1";

    UserDto userDto = userService.findByEmail(email);

    Assertions.assertEquals("Email 1", userDto.getEmail());
    Assertions.assertEquals("Name 1", userDto.getName());
    Assertions.assertEquals(UserRole.ROLE_USER, userDto.getRole());
  }

}
