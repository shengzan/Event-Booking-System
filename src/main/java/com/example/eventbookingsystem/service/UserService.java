package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.User;

public interface UserService {
    User registerUser(User user);
    User getUserByUsername(String username);
    boolean authenticateUser(String username, String password);
    // TODO: Add method to check if a username is already taken
    // TODO: Add method to check if an email is already registered
    // TODO: Add method to update user details
    // TODO: Add method to change user password
}
