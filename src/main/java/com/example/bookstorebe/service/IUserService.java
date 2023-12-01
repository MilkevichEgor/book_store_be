package com.example.bookstorebe.service;

import com.example.bookstorebe.dto.UserDto;
import com.example.bookstorebe.models.entity.User;

/**
 * Service class for managing users.
 */
public interface IUserService {


  /**
   * Registers a new user.
   *
   * @param userDto The signup request containing user details.
   * @return The user DTO object representing the newly registered user.
   * @throws RuntimeException if there is an error during user registration.
   */
  UserDto save(UserDto userDto) throws RuntimeException;

  /**
   * Retrieves the current user based on the provided authentication and principal.
   *
   * @param userId The ID of the user to retrieve.
   */
  UserDto getCurrentUser(Long userId);

  UserDto getById(Long userId);

  /**
   * Add the given book to the favorites list of the authenticated user.
   *
   * @param bookId The ID of the book to be added.
   * @param userId The ID of the user.
   * @return The response entity containing the user's updated favorites list.
   */
  UserDto addToFavorites(Long bookId, Long userId);

  /**
   * Removes a book from the user's favorites.
   *
   * @param bookId The ID of the book to be removed.
   * @param userId The ID of the user.
   * @return The response entity containing the user's updated favorites list.
   */
  UserDto removeFromFavorites(Long bookId, Long userId) throws Exception;

  UserDto toDto(User user, boolean attachLists);

  UserDto findByEmail(String userEmail);
}