package com.example.bookstorebe.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstorebe.config.WebSecurityConfig;
import com.example.bookstorebe.constant.UserRole;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.CommentRepository;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.security.JwtUtils;
import com.example.bookstorebe.service.IBookService;
import com.example.bookstorebe.service.ICommentService;
import com.example.bookstorebe.service.SocketService;
import io.socket.socketio.server.SocketIoNamespace;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {IBookService.class,
        ICommentService.class, BookRepository.class,
        UserRepository.class, CommentRepository.class})
@ComponentScan(basePackages = {"com.example.bookstorebe"})
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableConfigurationProperties
@AutoConfigureMockMvc
@Import(WebSecurityConfig.class)
class BookRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private SocketService socketService;

  @Autowired
  private JwtUtils jwtUtils;

  @MockBean
  private SocketIoNamespace namespace;

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

  @Test
  @DirtiesContext
//  @WithMockUser
  void getAllBooksTest() throws Exception {

    mockMvc.perform(get("/book/all")
                    .with(csrf())
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  @DirtiesContext
//  @WithMockUser(roles = "USER", value = "Name 1")
  void addCommentTest() throws Exception {
    User user = createUser("Name 1", "Email 1", "Password 1");
    Book book = createBook("Book 1", "Author 1", 30);
    socketService.setNamespace(namespace);
    bookRepository.save(book);
    userRepository.save(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    String token = jwtUtils.generateJwtToken(authentication);

    mockMvc.perform(post("/book/add-comment")
                    .with(csrf())
                    .header("Authorization", "Bearer " + token)
                    .contentType("application/json")
                    .content("{\"bookId\": 1, \"text\": \"Text\"}"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  @DirtiesContext
//  @WithMockUser(roles = "USER", value = "user")
  void getOneBookTest() throws Exception {
    User user = createUser("Name 1", "Email 1", "Password 1");
    Book book = createBook("Book 1", "Author 1", 30);
    bookRepository.save(book);
    userRepository.save(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    String token = jwtUtils.generateJwtToken(authentication);

    mockMvc.perform(get("/book/1")
                    .with(csrf())
                    .header("Authorization", "Bearer " + token)
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  @DirtiesContext
  void addFavoriteTest() throws Exception {

    User user = createUser("Name 1", "Email 1", "Password 1");
    Book book = createBook("Book 1", "Author 1", 30);
    bookRepository.save(book);
    userRepository.save(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    String token = jwtUtils.generateJwtToken(authentication);

    mockMvc.perform(post("/book/add-favorites")
                    .with(csrf())
                    .header("Authorization", "Bearer " + token)
                    .contentType("application/json")
                    .content("{\"bookId\": 1}"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  @DirtiesContext
  void removeFavoriteTest() throws Exception {

    User user = createUser("Name 1", "Email 1", "Password 1");
    Book book = createBook("Book 1", "Author 1", 30);

    List<Book> books = bookRepository.saveAll(List.of(book));
    user = userRepository.save(user);
    user.setFavorites(new HashSet<>(books));
    bookRepository.save(book);
    userRepository.save(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    String token = jwtUtils.generateJwtToken(authentication);

    mockMvc.perform(delete("/book/remove-favorites")
                    .with(csrf())
                    .header("Authorization", "Bearer " + token)
                    .contentType("application/json")
                    .content("{\"bookId\": 1}"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  @DirtiesContext
  void getFavoritesTest() throws Exception {

    User user = createUser("Name 1", "Email 1", "Password 1");
    Book book = createBook("Book 1", "Author 1", 30);
    List<Book> books = bookRepository.saveAll(List.of(book));
    user = userRepository.save(user);
    user.setFavorites(new HashSet<>(books));
    bookRepository.save(book);
    userRepository.save(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    String token = jwtUtils.generateJwtToken(authentication);

    mockMvc.perform(get("/book/favorites")
                    .with(csrf())
                    .header("Authorization", "Bearer " + token)
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  @DirtiesContext
  void getGenresTest() throws Exception {

    mockMvc.perform(get("/book/genres")
                    .with(csrf())
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();
  }
}
