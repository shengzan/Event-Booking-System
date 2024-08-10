package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.User;
import java.util.List;

public interface UserService {
    User registerUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean authenticateUser(String username, String password);
    boolean isUsernameTaken(String username);
    boolean isEmailRegistered(String email);
    User updateUserDetails(User user);
    boolean changeUserPassword(String username, String oldPassword, String newPassword);
    List<User> getAllUsers();
    void deleteUser(Long userId);
}
