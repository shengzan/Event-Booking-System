package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.model.User;

import java.util.List;

public interface TicketService {
    Ticket getTicketById(Long id);
    Ticket assignSeatNumber(Long ticketId, Integer seatNumber);
    Ticket updateTicketStatus(Long ticketId, Ticket.TicketStatus status, User user);
    List<Ticket> getTicketsByEvent(Long eventId);
    List<Ticket> getTicketsByUser(Long userId);
    List<Ticket> getAllTickets();
}
