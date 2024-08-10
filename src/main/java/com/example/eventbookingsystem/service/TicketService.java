package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Ticket;

public interface TicketService {
    Ticket orderTicket(Long eventId, String customerName, String customerEmail);
    Ticket getTicketById(Long id);
    
    // TODO: Implement method to assign seat number to a ticket
    // TODO: Implement method to update ticket status
    // TODO: Implement method to cancel a ticket
    // TODO: Implement method to get tickets by event
    // TODO: Implement method to get tickets by user
}
