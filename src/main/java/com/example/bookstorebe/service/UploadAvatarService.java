package com.example.bookstorebe.service;

import com.example.bookstorebe.DTO.UserResponse;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.repository.UserRepository;
import com.example.bookstorebe.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class UploadAvatarService {

    private final FilesStorageService storageService;
    private final UserRepository userRepository;

    public UploadAvatarService(FilesStorageService storageService,
                               UserRepository userRepository) {
        this.storageService = storageService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserResponse> updateAvatar(@RequestParam("avatar") MultipartFile avatar,
                                                     Authentication authentication) {
        try {


            User user = userRepository.findById(((UserDetailsImpl) authentication.getPrincipal()).getUser().getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID not found"));

            URI avatarUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .pathSegment(URLEncoder.encode(Objects.requireNonNull(avatar.getOriginalFilename()), StandardCharsets.UTF_8))
                    .build()
                    .toUri();


            if (!storageService.doesAvatarExist(avatar.getOriginalFilename())) {

                user.setAvatar(avatarUrl.toString());
                userRepository.save(user);
            } else {

                storageService.save(avatar);
                user.setAvatar(avatarUrl.toString());
                userRepository.save(user);
            }

            if (user.getAvatar() != null) {

                user.setAvatar(avatarUrl.toString());
                userRepository.save(user);
            }

            UserResponse userResponse = user.toResponse();

            String message = "Updated the avatar successfully: " + avatar.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } catch (ResponseStatusException e) {
            String message = "Could not update the avatar: " + avatar.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
}
