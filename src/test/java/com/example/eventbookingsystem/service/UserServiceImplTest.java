package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser() {
        User user = new User("testuser", "password", "test@example.com", User.UserRole.CUSTOMER);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("testuser", registeredUser.getUsername());
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
    }

    @Test
    void getUserByUsername() {
        String username = "testuser";
        User user = new User(username, "password", "test@example.com", User.UserRole.CUSTOMER);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByUsername(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    void getUserByEmail() {
        String email = "test@example.com";
        User user = new User("testuser", "password", email, User.UserRole.CUSTOMER);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }

    @Test
    void authenticateUser() {
        String username = "testuser";
        String password = "password";
        User user = new User(username, "encodedPassword", "test@example.com", User.UserRole.CUSTOMER);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        boolean authenticated = userService.authenticateUser(username, password);

        assertTrue(authenticated);
    }

    @Test
    void isUsernameTaken() {
        String username = "testuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        boolean taken = userService.isUsernameTaken(username);

        assertTrue(taken);
    }

    @Test
    void isEmailRegistered() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        boolean registered = userService.isEmailRegistered(email);

        assertTrue(registered);
    }

    @Test
    void updateUserDetails() {
        User existingUser = new User("testuser", "password", "test@example.com", User.UserRole.CUSTOMER);
        User updatedUser = new User("testuser", "password", "test@example.com", User.UserRole.CUSTOMER);
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Doe");
        updatedUser.setPhoneNumber("1234567890");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUserDetails(updatedUser);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("1234567890", result.getPhoneNumber());
    }

    @Test
    void changeUserPassword() {
        String username = "testuser";
        String oldPassword = "oldpassword";
        String newPassword = "newpassword";
        User user = new User(username, "encodedOldPassword", "test@example.com", User.UserRole.CUSTOMER);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        boolean changed = userService.changeUserPassword(username, oldPassword, newPassword);

        assertTrue(changed);
        verify(userRepository).save(user);
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(
            new User("user1", "password1", "user1@example.com", User.UserRole.CUSTOMER),
            new User("user2", "password2", "user2@example.com", User.UserRole.CUSTOMER)
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();

        assertEquals(2, allUsers.size());
        assertEquals("user1", allUsers.get(0).getUsername());
        assertEquals("user2", allUsers.get(1).getUsername());
    }

    @Test
    void deleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}
