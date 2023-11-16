package com.example.bookstorebe.service;

import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.security.requests.SignupRequest;
import com.example.bookstorebe.security.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public RegistrationService(UserRepository userRepository,
                               PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword())); // Хэширование пароля перед сохранением
        user.setUsername(signupRequest.getName());
        user.setRole(User.UserRole.USER);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
