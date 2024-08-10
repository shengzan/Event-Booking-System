package com.example.eventbookingsystem.model;

import jakarta.persistence.*;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String date;
    private int capacity;
    private String location;
    private double price;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    // TODO: Add getters and setters for all fields
    // TODO: Add constructors (default and parameterized)
    // TODO: Add validation annotations for name, description, date, capacity, location, and price
    // TODO: Implement toString() method
    // TODO: Implement equals() and hashCode() methods
    // TODO: Add method to check if the event has available capacity
    // TODO: Add method to decrease available capacity when a ticket is sold
}
