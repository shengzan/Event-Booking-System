package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.dto.LoginRequest;
import com.example.eventbookingsystem.dto.UserProfileDTO;
import com.example.eventbookingsystem.service.UserService;
import com.example.eventbookingsystem.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userService.isUsernameTaken(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        if (userService.isEmailRegistered(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already registered");
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt for user: {}", loginRequest.getUsername());
        if (userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())) {
            String token = jwtTokenProvider.createToken(loginRequest.getUsername());
            logger.info("User {} successfully authenticated", loginRequest.getUsername());
            return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body("User authenticated successfully");
        } else {
            logger.error("Authentication failed for user: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        if (authentication == null) {
            logger.warn("Attempt to access profile without authentication");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        String username = authentication.getName();
        logger.info("Fetching profile for user: {}", username);
        User user = userService.getUserByUsername(username);
        if (user != null) {
            UserProfileDTO profileDTO = new UserProfileDTO(user);
            return ResponseEntity.ok(profileDTO);
        }
        logger.warn("User not found for authenticated username: {}", username);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody User user, Authentication authentication) {
        if (!user.getUsername().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own profile");
        }
        User updatedUser = userService.updateUserDetails(user);
        return ResponseEntity.ok(updatedUser);
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
