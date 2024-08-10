package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Ticket;

public interface TicketService {
    Ticket orderTicket(Long eventId, String customerName, String customerEmail);
    Ticket getTicketById(Long id);
}
