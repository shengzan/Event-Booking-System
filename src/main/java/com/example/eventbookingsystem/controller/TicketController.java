package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> orderTicket(@RequestParam Long eventId, @RequestParam String customerName, @RequestParam String customerEmail, Authentication authentication) {
        // TODO: Implement orderTicket
        // TODO: Get user from authentication
        // TODO: Order ticket using ticketService
        // TODO: Assign seat number to the ticket
        // TODO: Return ResponseEntity with created ticket and appropriate status
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id, Authentication authentication) {
        // TODO: Implement getTicketById
        // TODO: Get user from authentication
        // TODO: Check if user is authorized to view this ticket
        // TODO: Get ticket using ticketService
        // TODO: Return ResponseEntity with ticket and appropriate status
        return null;
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelTicket(@PathVariable Long id, Authentication authentication) {
        // TODO: Implement cancelTicket
        // TODO: Get user from authentication
        // TODO: Check if user is authorized to cancel this ticket
        // TODO: Cancel ticket using ticketService
        // TODO: Return ResponseEntity with appropriate status
        return null;
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEvent(@PathVariable Long eventId, Authentication authentication) {
        // TODO: Implement getTicketsByEvent
        // TODO: Get user from authentication
        // TODO: Check if user is authorized to view tickets for this event
        // TODO: Get tickets by event using ticketService
        // TODO: Return ResponseEntity with list of tickets and appropriate status
        return null;
    }

    @GetMapping("/user")
    public ResponseEntity<List<Ticket>> getTicketsByUser(Authentication authentication) {
        // TODO: Implement getTicketsByUser
        // TODO: Get user from authentication
        // TODO: Get user's tickets using ticketService
        // TODO: Return ResponseEntity with list of tickets and appropriate status
        return null;
    }
}
