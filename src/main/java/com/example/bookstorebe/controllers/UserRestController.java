package com.example.bookstorebe.controllers;

import com.example.bookstorebe.dto.UserDto;
import com.example.bookstorebe.dto.response.OneFieldResponse;
import com.example.bookstorebe.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for user-related operations.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserRestController {

  private final UserService userService;

  @Autowired
  public UserRestController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retrieves the current user information.
   *
   * @param authentication the authentication object containing the user's credentials
   * @return a ResponseEntity containing a map with the user response
   */

  @Operation(summary = "Get current user",
      description = "Get current user information",
      tags = "User")
  @ApiResponses({
      @ApiResponse(responseCode = "200",
          description = "User retrieved successfully",
          content = @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")),
      @ApiResponse(responseCode = "500",
          description = "Internal server error, please try again",
          content = @Content(schema = @Schema()))
  })
  @GetMapping("/me")
  public ResponseEntity<Map<String, UserDto>> getCurrentUser(Authentication authentication) {
    Long userId = ((UserDto) authentication.getPrincipal()).getId();
    UserDto userDto;

    try {
      userDto = userService.getCurrentUser(userId);

    } catch (Exception e) {
      log.error("Get current user API failed", e);
      return ResponseEntity.internalServerError().body(null);
    }
    return ResponseEntity.ok(OneFieldResponse.of("user", userDto));
  }

  /**
   * Get user by ID.
   *
   * @param userId The ID of the user to retrieve.
   * @return The user object.
   */
  @Operation(summary = "Get user by ID",
      description = "Get user by ID",
      tags = "User")
  @ApiResponses({
      @ApiResponse(responseCode = "200",
          description = "User retrieved successfully",
          content = @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")),
  })
  @GetMapping(path = "/getUser")
  public UserDto getById(@RequestParam Long userId) {
    return userService.getById(userId);
  }

  /**
   * A description of the entire Java function.
   *
   * @param avatar         description of avatar parameter
   * @param authentication description of authentication parameter
   * @return description of return value
   */
  @Operation(summary = "Update avatar",
      description = "Update user's avatar",
      tags = "User")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User updated successfully",
          content = @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "Internal server error, please try again",
          content = @Content(schema = @Schema()))
  })
  @PostMapping(value = "/upload-avatar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<UserDto> updateAvatar(@RequestParam("avatar") MultipartFile avatar,
                                              Authentication authentication) {

    UserDto user = (UserDto) authentication.getPrincipal();

    try {
      return ResponseEntity.ok().body(userService.updateAvatar(avatar, user.getId()));
    } catch (Exception e) {
      log.error("Update avatar API failed", e);
      return ResponseEntity.internalServerError().body(null);
    }
  }
}
