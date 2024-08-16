package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Ticket;
import java.util.List;

public interface TicketService {
    Ticket orderTicket(Long eventId, String customerName, String customerEmail);
    Ticket getTicketById(Long id);
    Ticket assignSeatNumber(Long ticketId, Integer seatNumber);
    Ticket updateTicketStatus(Long ticketId, Ticket.TicketStatus status, User user);
    Ticket cancelTicket(Long ticketId);
    List<Ticket> getTicketsByEvent(Long eventId);
    List<Ticket> getTicketsByUser(Long userId);
    List<Ticket> getAllTickets();
}
