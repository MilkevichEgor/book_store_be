package com.example.bookstorebe.security.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
