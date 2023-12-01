package com.example.bookstorebe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request object for login.
 */
@Data
public class AuthRequest {

  @NotBlank
  private String email;

  @NotBlank
  private String password;

}