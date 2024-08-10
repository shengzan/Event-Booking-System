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

    // TODO: Add getters, setters, and constructors
    // TODO: Add validation annotations for username, password, and email
    // TODO: Add a method to check if the user is an Event Organizer
}
