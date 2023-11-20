package com.example.bookstorebe.security.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class SignupRequest {

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

}
