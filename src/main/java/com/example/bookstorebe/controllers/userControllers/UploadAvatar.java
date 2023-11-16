package com.example.bookstorebe.controllers.userControllers;

import com.example.bookstorebe.DTO.UserResponse;
import com.example.bookstorebe.service.UploadAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UploadAvatar {

    private final UploadAvatarService uploadAvatarService;

    @Autowired
    public UploadAvatar(UploadAvatarService uploadAvatarService) {
        this.uploadAvatarService = uploadAvatarService;
    }

    @PostMapping(value = "/upload-avatar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserResponse> updateAvatar(@RequestParam("avatar") MultipartFile avatar,
                                                        Authentication authentication) {
        return uploadAvatarService.updateAvatar(avatar, authentication);
    }
}
