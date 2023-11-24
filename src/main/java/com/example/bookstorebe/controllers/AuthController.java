package com.example.bookstorebe.controllers;

import com.example.bookstorebe.dto.UserDto;
import com.example.bookstorebe.dto.response.JwtResponse;
import com.example.bookstorebe.dto.response.MessageResponse;
import com.example.bookstorebe.security.jwt.JwtUtils;
import com.example.bookstorebe.security.requests.LoginRequest;
import com.example.bookstorebe.service.IUserService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication controller.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtUtils jwtUtils;
  @Autowired
  private IUserService userService;

  /**
   * Constructor.
   */
  @Autowired
  public AuthController(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  /**
   * Registers a user for signup.
   *
   * @param userDto the signup request object containing user details
   * @return a ResponseEntity object containing the response
   */

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody UserDto userDto) {

    try {
      userService.save(userDto);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
    }

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> signin(@RequestBody LoginRequest loginRequest) {

    UserDto user;
    String jwt;
    String refreshToken;

    try {
      user = userService.findByEmail(loginRequest.getEmail());

      Authentication authentication = new UsernamePasswordAuthenticationToken(
              user.getEmail(), user.getPassword(), new ArrayList<>());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      jwt = jwtUtils.generateJwtToken(authentication);
      refreshToken = "42";

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }
    return ResponseEntity.ok(new JwtResponse(user, jwt, refreshToken, "You are signed in"));
  }

  /**
   * Checks the validity of a token.
   *
   * @param token the token to be checked
   * @return a ResponseEntity describing the token's validity and the corresponding HTTP status
   */
  @PostMapping("/checkToken")
  public ResponseEntity<String> checkToken(@RequestParam String token) {
    if (jwtUtils.validateJwtToken(token)) {
      return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Token is invalid", HttpStatus.UNAUTHORIZED);
    }
  }
}
