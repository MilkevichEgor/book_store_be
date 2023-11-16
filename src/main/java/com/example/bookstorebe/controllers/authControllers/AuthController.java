package com.example.bookstorebe.controllers.authControllers;

import com.example.bookstorebe.security.jwt.JwtUtils;
import com.example.bookstorebe.security.requests.LoginRequest;
import com.example.bookstorebe.security.requests.SignupRequest;
import com.example.bookstorebe.service.AuthService;
import com.example.bookstorebe.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final AuthService authService;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(JwtUtils jwtUtils,
                          AuthService authService,
                          RegistrationService registrationService) {
        this.jwtUtils = jwtUtils;
        this.authService = authService;
        this.registrationService = registrationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.signin(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return registrationService.registerUser(signupRequest);
    }

    @PostMapping("/checkToken")
    public ResponseEntity<String> checkToken(@RequestParam String token) {
        if (jwtUtils.validateJwtToken(token)) {
            return new ResponseEntity<>("Token is valid", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token is invalid", HttpStatus.UNAUTHORIZED);
        }
    }


}
