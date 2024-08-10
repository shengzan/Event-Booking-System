package com.example.eventbookingsystem.repository;

import com.example.eventbookingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // TODO: Add method to find user by username
    // TODO: Add method to find user by email
}
