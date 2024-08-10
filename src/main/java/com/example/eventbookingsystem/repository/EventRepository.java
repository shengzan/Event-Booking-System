package com.example.eventbookingsystem.repository;

import com.example.eventbookingsystem.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Spring Data JPA will automatically implement basic CRUD operations
}
