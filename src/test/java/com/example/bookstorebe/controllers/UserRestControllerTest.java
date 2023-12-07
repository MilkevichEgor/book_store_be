package com.example.bookstorebe.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstorebe.constant.UserRole;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.CommentRepository;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.security.JwtUtils;
import com.example.bookstorebe.service.IBookService;
import com.example.bookstorebe.service.ICommentService;
import com.example.bookstorebe.service.IUserService;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {IUserService.class,
        ICommentService.class, BookRepository.class,
        UserRepository.class, CommentRepository.class, IBookService.class})
@ComponentScan(basePackages = {"com.example.bookstorebe"})
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableConfigurationProperties
@AutoConfigureMockMvc
class UserRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtils jwtUtils;


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
  void getCurrentUserTest() throws Exception {
    User user = createUser("Name 1", "Email 1", "Password 1");
    userRepository.save(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    String token = jwtUtils.generateJwtToken(authentication);

    mockMvc.perform(get("/user/me")
                    .with(csrf())
                    .header("Authorization", "Bearer " + token)
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  @DirtiesContext
  void uploadAvatarTest() throws Exception {
    User user = createUser("Name 1", "Email 1", "Password 1");
    userRepository.save(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    String token = jwtUtils.generateJwtToken(authentication);

    MockMultipartFile file = new MockMultipartFile(
            "avatar", "test.png", null, "test".getBytes());

    mockMvc.perform(multipart("/user/upload-avatar")
                    .file(file)
                    .with(csrf())
                    .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andReturn();
  }
}
