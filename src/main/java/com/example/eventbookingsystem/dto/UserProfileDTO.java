package com.example.eventbookingsystem.dto;

import com.example.eventbookingsystem.model.User;

public class UserProfileDTO {
    private Long id;
    private String username;
    private String email;
    private User.UserRole role;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public UserProfileDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public User.UserRole getRole() { return role; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
}
