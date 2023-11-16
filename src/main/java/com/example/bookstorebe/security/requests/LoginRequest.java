package com.example.bookstorebe.security.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LoginRequest {

    @Getter
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
