package com.example.bookstorebe.controllers.userControllers;

import com.example.bookstorebe.DTO.UserResponse;
import com.example.bookstorebe.models.entity.User;
import com.example.bookstorebe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class getUser {

    private final UserService userService;

    @Autowired
    public getUser(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, UserResponse>> getCurrentUser(Authentication authentication, Principal principal) {
        return userService.getCurrentUser(authentication, principal);
    }

    @GetMapping(path = "/getUser")
    public @ResponseBody User getById(@RequestParam Integer userId) {
        return userService.getById(userId);
    }

}
