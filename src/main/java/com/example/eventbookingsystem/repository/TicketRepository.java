package com.example.eventbookingsystem.repository;

import com.example.eventbookingsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Spring Data JPA will automatically implement basic CRUD operations
}
