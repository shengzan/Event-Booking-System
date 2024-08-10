package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Ticket;

public interface TicketService {
    Ticket orderTicket(Long eventId, String customerName, String customerEmail);
    Ticket getTicketById(Long id);
    Ticket assignSeatNumber(Long ticketId, Integer seatNumber);
    Ticket updateTicketStatus(Long ticketId, String status);
    Ticket cancelTicket(Long ticketId);
    List<Ticket> getTicketsByEvent(Long eventId);
    List<Ticket> getTicketsByUser(Long userId);
    List<Ticket> getAllTickets();
}
