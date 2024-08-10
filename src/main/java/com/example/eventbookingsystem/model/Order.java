package com.example.eventbookingsystem.model;

import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDateTime;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime orderDate;

    // TODO: Add getters and setters for all fields
    // TODO: Add constructors (default and parameterized)
    // TODO: Implement toString() method
    // TODO: Implement equals() and hashCode() methods
    // TODO: Add method to calculate total order price
}
