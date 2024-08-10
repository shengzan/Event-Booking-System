package com.example.eventbookingsystem.repository;

import com.example.eventbookingsystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Spring Data JPA will automatically implement basic CRUD operations
}
