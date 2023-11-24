package com.example.bookstorebe.dto.response;

import com.example.bookstorebe.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity user for response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private UserDto user;
}
