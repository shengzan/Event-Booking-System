package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        boolean isAuthenticated = userService.authenticateUser(username, password);
        if (isAuthenticated) {
            // In a real application, you would generate and return a JWT token here
            return ResponseEntity.ok("User authenticated successfully");
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody User user, Authentication authentication) {
        if (user.getUsername().equals(authentication.getName())) {
            User updatedUser = userService.updateUserDetails(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Authentication authentication) {
        boolean passwordChanged = userService.changeUserPassword(authentication.getName(), oldPassword, newPassword);
        if (passwordChanged) {
            return ResponseEntity.ok("Password changed successfully");
        }
        return ResponseEntity.badRequest().body("Failed to change password");
    }
}
