package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.TicketService;
import com.example.eventbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;
    private final EventService eventService;

    public TicketController(TicketService ticketService, UserService userService, EventService eventService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestParam Long eventId, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            Event event = eventService.getEventById(eventId);
            if (event == null) {
                return ResponseEntity.notFound().build();
            }
            Ticket ticket = ticketService.createTicket(event, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating ticket: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Ticket> orderTicket(@RequestParam Long eventId, @RequestParam String customerName, @RequestParam String customerEmail, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Ticket ticket = ticketService.orderTicket(eventId, customerName, customerEmail);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket != null && (ticket.getUser().getId().equals(user.getId()) || user.getRole() == User.UserRole.ADMIN)) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelTicket(@PathVariable Long id, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket != null && (ticket.getUser().getId().equals(user.getId()) || user.getRole() == User.UserRole.ADMIN)) {
            Ticket cancelledTicket = ticketService.cancelTicket(id);
            return ResponseEntity.ok(cancelledTicket);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<?> updateTicketStatus(@PathVariable Long id, @RequestParam Ticket.TicketStatus status, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Ticket updatedTicket = ticketService.updateTicketStatus(id, status, user);
        if (updatedTicket != null) {
            return ResponseEntity.ok(updatedTicket);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEvent(@PathVariable Long eventId, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (user.getRole() == User.UserRole.ADMIN || user.getRole() == User.UserRole.ORGANIZER) {
            List<Ticket> tickets = ticketService.getTicketsByEvent(eventId);
            return ResponseEntity.ok(tickets);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<Ticket>> getTicketsByUser(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        List<Ticket> tickets = ticketService.getTicketsByUser(user.getId());
        return ResponseEntity.ok(tickets);
    }
}
