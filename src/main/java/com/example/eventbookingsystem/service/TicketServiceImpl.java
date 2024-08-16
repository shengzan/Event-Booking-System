package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.repository.EventRepository;
import com.example.eventbookingsystem.repository.TicketRepository;
import com.example.eventbookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Ticket orderTicket(Long eventId, String customerName, String customerEmail) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        if (!event.hasAvailableCapacity()) {
            throw new RuntimeException("No available tickets for this event");
        }
        User user = userRepository.findByEmail(customerEmail).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(customerEmail);
            newUser.setUsername(customerEmail); // Using email as username for simplicity
            newUser.setFirstName(customerName.split(" ")[0]);
            newUser.setLastName(customerName.split(" ").length > 1 ? customerName.split(" ")[1] : "");
            return userRepository.save(newUser);
        });
        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setUser(user);
        ticket.setStatus(Ticket.TicketStatus.RESERVED);
        event.decreaseAvailableCapacity();
        eventRepository.save(event);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public Ticket assignSeatNumber(Long ticketId, Integer seatNumber) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setSeatNumber(seatNumber);
            return ticketRepository.save(ticket);
        }
        return null;
    }

    @Override
    public Ticket updateTicketStatus(Long ticketId, Ticket.TicketStatus status) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setStatus(status);
            return ticketRepository.save(ticket);
        }
        return null;
    }

    @Override
    public Ticket cancelTicket(Long ticketId) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setStatus(Ticket.TicketStatus.CANCELED);
            Event event = ticket.getEvent();
            event.setAvailableCapacity(event.getAvailableCapacity() + 1);
            eventRepository.save(event);
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
