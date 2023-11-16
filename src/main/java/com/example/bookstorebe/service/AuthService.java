package com.example.bookstorebe.service;

import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.security.jwt.JwtUtils;
import com.example.bookstorebe.security.requests.LoginRequest;
import com.example.bookstorebe.security.response.JwtResponse;
import com.example.bookstorebe.security.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder encoder,
                       JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> signin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String userEmail = loginRequest.getEmail();
            User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userEmail));

            if (user == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("User not found."));
            }

            // Сравнение хэшей паролей
            if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Incorrect password."));
            }

            // Создание аутентификации
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword(),
                    new ArrayList<>()

            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            String refreshToken = "42";

            return ResponseEntity.ok(
                    new JwtResponse(
                            user.toResponse(),
                            jwt,
                            refreshToken,
                            "You are signed in"
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }

    }
}
