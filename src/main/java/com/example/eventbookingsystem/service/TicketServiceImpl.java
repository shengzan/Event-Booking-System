package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.repository.EventRepository;
import com.example.eventbookingsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Ticket createTicket(Event event, Order order, User user) {
        Ticket ticket = new Ticket(event, order, user, Ticket.TicketStatus.PAID);
        ticket = assignSeatNumber(ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public Ticket assignSeatNumber(Ticket ticket) {
        Event event = ticket.getEvent();
        List<Ticket> eventTickets = ticketRepository.findByEventId(event.getId());
        
        // Find the highest seat number for this event
        int highestSeatNumber = eventTickets.stream()
            .map(Ticket::getSeatNumber)
            .filter(Objects::nonNull)
            .max(Integer::compareTo)
            .orElse(0);
        
        // Assign the next seat number
        int newSeatNumber = highestSeatNumber + 1;
        ticket.setSeatNumber(newSeatNumber);
        return ticket;
    }

    @Override
    public Ticket updateTicketStatus(Long ticketId, Ticket.TicketStatus status, User user) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setStatus(status);
            ticket.setUser(user);
            return ticketRepository.save(ticket);
        }
        return null;
    }

    @Override
    public List<Ticket> getTicketsByEvent(Long eventId) {
        return ticketRepository.findByEventId(eventId);
    }

    @Override
    public List<Ticket> getTicketsByUser(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
