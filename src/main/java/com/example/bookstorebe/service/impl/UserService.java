package com.example.bookstorebe.service.impl;

import com.example.bookstorebe.constant.UserRole;
import com.example.bookstorebe.dto.BookDto;
import com.example.bookstorebe.dto.RatingDto;
import com.example.bookstorebe.dto.UserDto;
import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.BookRepository;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.service.FilesStorageService;
import com.example.bookstorebe.service.IBookService;
import com.example.bookstorebe.service.IUserService;
import com.example.bookstorebe.service.RatingService;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Service class for managing users.
 */
@Service
@EnableCaching
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final FilesStorageService storageService;
  private final RatingService ratingService;
  @Autowired
  private IBookService bookService;
  @Autowired
  private BookRepository bookRepository;

  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  /**
   * Constructor for UserService.
   */
  @Autowired
  public UserService(UserRepository userRepository,
                     PasswordEncoder encoder,
                     FilesStorageService storageService,
                     RatingService ratingService) {
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.storageService = storageService;
    this.ratingService = ratingService;
  }

  /**
   * Registers a new user.
   *
   * @param userDto The signup request containing user details.
   * @return The user DTO object representing the newly registered user.
   * @throws RuntimeException if the provided email already exists in the database.
   */
  @Transactional
  public UserDto save(UserDto userDto) throws RuntimeException {
    if (userRepository.existsByEmail(userDto.getEmail())) {
      logger.error("Email {} already exists", userDto.getEmail());
      throw new RuntimeException("Email already exists");
    }

    User user = new User();
    user.setEmail(userDto.getEmail());
    user.setPassword(encoder.encode(userDto.getPassword()));
    user.setName(userDto.getUsername());
    user.setRole(UserRole.ROLE_USER);

    return toDto(userRepository.save(user), false);
  }

  /**
   * Retrieves the current user based on the provided authentication and principal.
   *
   * @param userId The ID of the user to retrieve.
   */
  public UserDto getCurrentUser(Long userId) {
    User user = userRepository.findById(userId).get();
    return toDto(user, true);
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param userId The ID of the user to retrieve.
   * @return The user with the specified ID.
   */
  public UserDto getById(Long userId) {

    return toDto(userRepository.findById(userId).get(), true);
  }

  /**
   * Updates the avatar of a user.
   *
   * @param avatar the avatar file to be uploaded
   * @param userId the ID of the user to be updated
   * @return the response entity with the updated user information
   */
  @Transactional
  public UserDto updateAvatar(MultipartFile avatar,
                              Long userId) {

    User user = userRepository.findById(userId).get();
    URI avatarUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/uploads/")
            .pathSegment(URLEncoder.encode(Objects.requireNonNull(avatar.getOriginalFilename()),
                    StandardCharsets.UTF_8))
            .build()
            .toUri();

    if (!storageService.doesAvatarExist(avatar.getOriginalFilename())) {
      storageService.save(avatar);
    }
    user.setAvatar(avatarUrl.toString());
    userRepository.save(user);

    if (user.getAvatar() != null) {

      user.setAvatar(avatarUrl.toString());
      userRepository.save(user);
    }
    return toDto(user, true);
  }

  /**
   * Add the given book to the favorites list of the authenticated user.
   *
   * @param bookId The ID of the book to be added.
   * @param userId The ID of the user.
   * @return The response entity containing the user's updated favorites list.
   */
  @Transactional
  public UserDto addToFavorites(Long bookId, Long userId) {

    Book book = bookRepository.findById(bookId).get();
    User user = userRepository.findById(userId).get();

    user.getFavorites().add(book);

    return toDto(userRepository.save(user), true);
  }

  /**
   * Removes a book from the user's favorites.
   *
   * @param bookId The ID of the book to be removed.
   * @param userId The ID of the user.
   * @return The response entity containing the user's updated favorites list.
   */
  @Transactional
  public UserDto removeFromFavorites(Long bookId, Long userId) throws Exception {
    User user = userRepository.findById(userId).get();
    user.getFavorites().remove(bookRepository.findById(bookId).get());

    Book book = bookRepository.findById(bookId).get();
    book.getUsers().remove(user);

    UserDto result = toDto(userRepository.save(user), true);
    bookRepository.save(book);

    return result;
  }

  /**
   * Converts a User object to a UserDto object.
   *
   * @param user        the User object to be converted
   * @param attachLists boolean flag indicating whether to attach lists or not
   * @return the converted UserDto object
   */
  public UserDto toDto(User user, boolean attachLists) {

    List<RatingDto> ratings = new ArrayList<>();
    List<BookDto> favorites = new ArrayList<>();
    if (attachLists) {
      user.getRatings().forEach(rating -> ratings.add(ratingService.toDto(rating, false)));
      user.getFavorites().forEach(book -> favorites.add(bookService.toDto(book, false)));
    }
    return new UserDto(
            user.getUserId(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getAvatar(),
            user.getRole(),
            ratings,
            favorites
    );
  }

  @Cacheable("users")
  @Override
  public UserDto findByEmail(String userEmail) {
    return toDto(userRepository.findByEmail(userEmail).get(), true);
  }
}