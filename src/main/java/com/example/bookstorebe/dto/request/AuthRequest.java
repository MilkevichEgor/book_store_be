package com.example.bookstorebe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for login.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

  @NotBlank
  private String email;

  @NotBlank
  private String password;

}