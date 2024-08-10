package com.example.eventbookingsystem.repository;

import com.example.eventbookingsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventId(Long eventId);
    List<Ticket> findByUserId(Long userId);
}
