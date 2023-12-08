package com.example.bookstorebe.controllers;

import com.example.bookstorebe.dto.UserDto;
import com.example.bookstorebe.dto.request.AuthRequest;
import com.example.bookstorebe.dto.response.JwtResponse;
import com.example.bookstorebe.dto.response.MessageResponse;
import com.example.bookstorebe.security.JwtUtils;
import com.example.bookstorebe.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
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
 *
 * @author Egor Milkevich
 */
@Tag(name = "Auth", description = "Authentication API")
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthRestController {

  private final JwtUtils jwtUtils;
  @Autowired
  private IUserService userService;

  /**
   * Constructor.
   */
  @Autowired
  public AuthRestController(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  /**
   * Registers a user for signup.
   *
   * @param request the signup request object containing user details
   * @return a ResponseEntity object containing the response
   */
  @Operation(summary = "Register a new user",
      description = "Registers a new user with the provided email and password.",
      tags = "Auth")
  @ApiResponses({
      @ApiResponse(responseCode = "200",
          description = "User registered successfully",
          content = @Content(schema = @Schema(implementation
              = MessageResponse.class), mediaType = "application/json")),
      @ApiResponse(responseCode = "400",
          description = "Error: Bad request, please check the request and try again",
          content = @Content(schema = @Schema(implementation
              = MessageResponse.class), mediaType = "application/json")),
  })
  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody AuthRequest request) {

    try {
      userService.save(new UserDto(request.getEmail(), request.getPassword()));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
    }
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  /**
   * Sign in endpoint for the API.
   *
   * @param authRequest The login request object containing the user's email and password.
   * @return The ResponseEntity containing the JwtResponse with the user's details and tokens.
   */
  @Operation(summary = "Authenticate a user",
      description = "Authenticate a user with the provided email and password.",
      tags = "Auth")
  @Parameters({
      @Parameter(name = "email", description = "The email of the user"),
      @Parameter(name = "password", description = "The password of the user")
  })
  @ApiResponses({
      @ApiResponse(responseCode = "200",
          description = "User authenticated successfully",
          content = @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json")),
      @ApiResponse(responseCode = "400",
          description = "Error: Invalid credentials, please check the request",
          content = @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json")),
      @ApiResponse(responseCode = "500",
          description = "Internal server error, check connection with database, and try again",
          content = @Content(schema = @Schema()))
  })
  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> signin(@RequestBody AuthRequest authRequest) {

    UserDto user;
    String jwt;
    String refreshToken;

    try {
      user = userService.findByEmail(authRequest.getEmail());

      Authentication authentication = new UsernamePasswordAuthenticationToken(
          user.getEmail(), user.getPassword(), new ArrayList<>());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      jwt = jwtUtils.generateJwtToken(authentication);
      refreshToken = "42";

    } catch (Exception e) {
      log.error("Sign in API failed", e);
      return ResponseEntity.internalServerError().body(null);
    }
    return ResponseEntity.ok(new JwtResponse(user, jwt, refreshToken, "You are signed in"));
  }

  /**
   * Checks the validity of a token.
   *
   * @param token the token to be checked
   * @return a ResponseEntity describing the token's validity and the corresponding HTTP status
   */
  @Operation(summary = "Check token validity",
      description = "Checks the validity of a token.",
      tags = "Auth")
  @ApiResponses({
      @ApiResponse(responseCode = "200",
          description = "Token is valid",
          content = @Content(schema = @Schema(), mediaType = "application/json")),
      @ApiResponse(responseCode = "401",
          description = "Token is invalid, please sign in",
          content = @Content(schema = @Schema(), mediaType = "application/json"))
  })
  @PostMapping("/checkToken")
  public ResponseEntity<String> checkToken(@RequestParam String token) {
    if (jwtUtils.validateJwtToken(token)) {
      return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Token is invalid", HttpStatus.UNAUTHORIZED);
    }
  }
}
