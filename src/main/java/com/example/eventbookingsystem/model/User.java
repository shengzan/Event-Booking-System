package com.example.eventbookingsystem.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    // TODO: Add getters and setters for all fields
    // TODO: Add constructors (default and parameterized)
    // TODO: Add validation annotations for username, password, email, firstName, lastName, and phoneNumber
    // TODO: Implement a method to check if the user is an Event Organizer
    // TODO: Implement toString() method
    // TODO: Implement equals() and hashCode() methods
}
