package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.dto.UserProfileDTO;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserProfile_Success() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setEmail("test@example.com");
        user.setRole(User.UserRole.USER);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPhoneNumber("+1234567890");

        when(authentication.getName()).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);

        // Act
        ResponseEntity<?> response = userController.getUserProfile(authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof UserProfileDTO);
        UserProfileDTO profileDTO = (UserProfileDTO) response.getBody();
        assertEquals(user.getId(), profileDTO.getId());
        assertEquals(user.getUsername(), profileDTO.getUsername());
        assertEquals(user.getEmail(), profileDTO.getEmail());
        assertEquals(user.getRole(), profileDTO.getRole());
        assertEquals(user.getFirstName(), profileDTO.getFirstName());
        assertEquals(user.getLastName(), profileDTO.getLastName());
        assertEquals(user.getPhoneNumber(), profileDTO.getPhoneNumber());

        verify(authentication).getName();
        verify(userService).getUserByUsername(username);
    }

    @Test
    void testGetUserProfile_UserNotFound() {
        // Arrange
        String username = "nonexistentuser";
        when(authentication.getName()).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(null);

        // Act
        ResponseEntity<?> response = userController.getUserProfile(authentication);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(authentication).getName();
        verify(userService).getUserByUsername(username);
    }

    @Test
    void testGetUserProfile_Unauthenticated() {
        // Arrange
        when(authentication.isAuthenticated()).thenReturn(false);

        // Act
        ResponseEntity<?> response = userController.getUserProfile(authentication);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not authenticated", response.getBody());

        verify(authentication).isAuthenticated();
        verifyNoMoreInteractions(userService);
    }
}
