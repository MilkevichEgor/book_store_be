package com.example.bookstorebe.dto.response;

import com.example.bookstorebe.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a JSON Web Token (JWT) response.
 * This class contains information about the user.
 */
@Data
@AllArgsConstructor
public class JwtResponse {

  private final UserDto user;
  private String token;
  private String refreshToken;
  private String message;

}
