package com.example.bookstorebe.security.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SignupRequest {

    @Getter
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @Nullable
    private String avatar;

    @Nullable
    private String name;

    @Nullable
    private Date dob;

    @Nullable
    private Set<String> role = new HashSet<>(Collections.singletonList("USER"));

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

}
