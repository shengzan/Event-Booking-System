package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public Ticket orderTicket(@RequestParam Long eventId, @RequestParam String customerName, @RequestParam String customerEmail) {
        // TODO: Implement orderTicket
        return null;
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        // TODO: Implement getTicketById
        return null;
    }
}
