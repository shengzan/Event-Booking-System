package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // TODO: Validate user input
        // TODO: Check if username or email already exists
        // TODO: Encrypt password
        // TODO: Save user to database
        // TODO: Return appropriate response
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        // TODO: Validate user input
        // TODO: Authenticate user
        // TODO: Generate and return JWT token if authentication successful
        // TODO: Return appropriate response
        return null;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam String username) {
        // TODO: Implement get user profile
        return null;
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody User user) {
        // TODO: Implement update user profile
        return null;
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        // TODO: Implement change password
        return null;
    }
}
