package com.example.eventbookingsystem.model;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer seatNumber;

    // TODO: Add getters and setters for all fields
    // TODO: Add constructors (default and parameterized)
    // TODO: Implement toString() method
    // TODO: Implement equals() and hashCode() methods
    // TODO: Add method to get ticket price from associated event
}
