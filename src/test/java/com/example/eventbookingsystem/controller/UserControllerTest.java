package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.dto.LoginRequest;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.UserService;
import com.example.eventbookingsystem.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import java.util.ArrayList;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("123456");
        user.setRole(User.UserRole.ORGANIZER);

        when(userService.isUsernameTaken("testuser")).thenReturn(false);
        when(userService.isEmailRegistered("test@example.com")).thenReturn(false);
        when(userService.registerUser(user)).thenReturn(user);

        // Act
        ResponseEntity<?> response = userController.registerUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService).isUsernameTaken("testuser");
        verify(userService).isEmailRegistered("test@example.com");
        verify(userService).registerUser(user);
    }

    @Test
    void testRegisterUser_UsernameTaken() {
        // Arrange
        User user = new User();
        user.setUsername("existinguser");
        user.setEmail("test@example.com");

        when(userService.isUsernameTaken("existinguser")).thenReturn(true);

        // Act
        ResponseEntity<?> response = userController.registerUser(user);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username is already taken", response.getBody());
        verify(userService).isUsernameTaken("existinguser");
        verify(userService, never()).isEmailRegistered(anyString());
        verify(userService, never()).registerUser(any(User.class));
    }

    @Test
    void testRegisterUser_EmailTaken() {
        // Arrange
        User user = new User();
        user.setUsername("newuser");
        user.setEmail("existing@example.com");

        when(userService.isUsernameTaken("newuser")).thenReturn(false);
        when(userService.isEmailRegistered("existing@example.com")).thenReturn(true);

        // Act
        ResponseEntity<?> response = userController.registerUser(user);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email is already registered", response.getBody());
        verify(userService).isUsernameTaken("newuser");
        verify(userService).isEmailRegistered("existing@example.com");
        verify(userService, never()).registerUser(any(User.class));
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        Authentication authentication = mock(Authentication.class);
        org.springframework.security.core.userdetails.User userDetails = 
            new org.springframework.security.core.userdetails.User("testuser", "password123", new ArrayList<>());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtTokenProvider.createToken("testuser")).thenReturn("test-jwt-token");

        // Act
        ResponseEntity<?> response = userController.loginUser(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User authenticated successfully", response.getBody());
        assertEquals("Bearer test-jwt-token", response.getHeaders().getFirst("Authorization"));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).createToken("testuser");
    }

    @Test
    void testLoginUser_Failure() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Invalid username or password"));

        // Act
        ResponseEntity<?> response = userController.loginUser(loginRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtTokenProvider);
    }
}
