package com.example.bookstorebe.security.response;

import com.example.bookstorebe.DTO.UserResponse;
import lombok.Getter;

public class JwtResponse {

    @Getter
    private final UserResponse user;

    @Getter
    private String token;

    @Getter
    private String refreshToken;

    @Getter
    private String message;

    public JwtResponse(UserResponse user, String token, String refreshToken, String message) {
        this.user = user;
        this.token = token;
        this.refreshToken = refreshToken;
        this.message = message;
    }

}
