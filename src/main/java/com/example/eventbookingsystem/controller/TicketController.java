package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.EventService;
import com.example.eventbookingsystem.service.TicketService;
import com.example.eventbookingsystem.service.UserService;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            Ticket ticket = ticketService.getTicketById(id);
            if (ticket != null && (ticket.getUser().getId().equals(user.getId()) || user.getRole() == User.UserRole.ADMIN)) {
                return ResponseEntity.ok(ticket);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to view this ticket");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving ticket: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelTicket(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            Ticket ticket = ticketService.getTicketById(id);
            if (ticket != null && (ticket.getUser().getId().equals(user.getId()) || user.getRole() == User.UserRole.ADMIN)) {
                Ticket cancelledTicket = ticketService.updateTicketStatus(id, Ticket.TicketStatus.CANCELED);
                return ResponseEntity.ok(cancelledTicket);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to cancel this ticket");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error cancelling ticket: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<?> updateTicketStatus(@PathVariable Long id, @RequestParam Ticket.TicketStatus status, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (user.getRole() == User.UserRole.ADMIN) {
                Ticket updatedTicket = ticketService.updateTicketStatus(id, status);
                if (updatedTicket != null) {
                    return ResponseEntity.ok(updatedTicket);
                }
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only administrators can update ticket status");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating ticket status: " + e.getMessage());
        }
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getTicketsByEvent(@PathVariable Long eventId, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (user.getRole() == User.UserRole.ADMIN || user.getRole() == User.UserRole.ORGANIZER) {
                List<Ticket> tickets = ticketService.getTicketsByEvent(eventId);
                return ResponseEntity.ok(tickets);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only administrators and organizers can view tickets by event");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving tickets by event: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getTicketsByUser(Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            List<Ticket> tickets = ticketService.getTicketsByUser(user.getId());
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving user tickets: " + e.getMessage());
        }
    }
}
