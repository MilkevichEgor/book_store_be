package com.example.bookstorebe.security.response;

import com.example.bookstorebe.DTO.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class JwtResponse {

    private final UserResponse user;

    private String token;

    private String refreshToken;

    private String message;

}
