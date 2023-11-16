package com.example.bookstorebe.service;

import com.example.bookstorebe.DTO.UserResponse;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<String, UserResponse>> getCurrentUser(Authentication authentication,
                                                                    Principal principal) {
        if (principal == null) {
            return ResponseEntity.notFound().build();
        }

        User userResponse = userRepository.findById(((UserDetailsImpl) authentication.getPrincipal()).getUser().getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID not found"));

        Map<String, UserResponse> response = new HashMap<>();
        response.put("user", userResponse.toResponse());

        return ResponseEntity.ok(response);
    }

    public @ResponseBody User getById(@RequestParam Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }
}