package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.repository.EventRepository;
import com.example.eventbookingsystem.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTicket() {
        // Arrange
        Event event = new Event();
        event.setId(1L);
        Order order = new Order();
        User user = new User();
        Ticket expectedTicket = new Ticket(event, order, user, Ticket.TicketStatus.PAID);

        when(ticketRepository.save(any(Ticket.class))).thenReturn(expectedTicket);

        // Act
        Ticket createdTicket = ticketService.createTicket(event, order, user);

        // Assert
        assertNotNull(createdTicket);
        assertEquals(expectedTicket, createdTicket);
        assertEquals(Ticket.TicketStatus.PAID, createdTicket.getStatus());
        verify(eventRepository).save(event);
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void getTicketById() {
        // Arrange
        Long ticketId = 1L;
        Ticket expectedTicket = new Ticket();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(expectedTicket));

        // Act
        Ticket actualTicket = ticketService.getTicketById(ticketId);

        // Assert
        assertEquals(expectedTicket, actualTicket);
        verify(ticketRepository).findById(ticketId);
    }

    @Test
    void assignSeatNumber() {
        // Arrange
        Event event = new Event();
        event.setId(1L);
        Ticket ticket = new Ticket();
        ticket.setEvent(event);

        List<Ticket> existingTickets = Arrays.asList(
            createTicketWithSeatNumber(1),
            createTicketWithSeatNumber(2),
            createTicketWithSeatNumber(3)
        );

        when(ticketRepository.findByEventId(event.getId())).thenReturn(existingTickets);

        // Act
        Ticket ticketWithSeat = ticketService.assignSeatNumber(ticket);

        // Assert
        assertEquals(4, ticketWithSeat.getSeatNumber());
        verify(ticketRepository).findByEventId(event.getId());
    }

    @Test
    void updateTicketStatus() {
        // Arrange
        Long ticketId = 1L;
        Ticket.TicketStatus newStatus = Ticket.TicketStatus.CANCELED;
        Ticket existingTicket = new Ticket();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        // Act
        Ticket updatedTicket = ticketService.updateTicketStatus(ticketId, newStatus);

        // Assert
        assertNotNull(updatedTicket);
        assertEquals(newStatus, updatedTicket.getStatus());
        verify(ticketRepository).findById(ticketId);
        verify(ticketRepository).save(existingTicket);
    }

    @Test
    void getTicketsByEvent() {
        // Arrange
        Long eventId = 1L;
        List<Ticket> expectedTickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepository.findByEventId(eventId)).thenReturn(expectedTickets);

        // Act
        List<Ticket> actualTickets = ticketService.getTicketsByEvent(eventId);

        // Assert
        assertEquals(expectedTickets, actualTickets);
        verify(ticketRepository).findByEventId(eventId);
    }

    @Test
    void getTicketsByUser() {
        // Arrange
        Long userId = 1L;
        List<Ticket> expectedTickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepository.findByUserId(userId)).thenReturn(expectedTickets);

        // Act
        List<Ticket> actualTickets = ticketService.getTicketsByUser(userId);

        // Assert
        assertEquals(expectedTickets, actualTickets);
        verify(ticketRepository).findByUserId(userId);
    }

    @Test
    void getAllTickets() {
        // Arrange
        List<Ticket> expectedTickets = Arrays.asList(new Ticket(), new Ticket(), new Ticket());
        when(ticketRepository.findAll()).thenReturn(expectedTickets);

        // Act
        List<Ticket> actualTickets = ticketService.getAllTickets();

        // Assert
        assertEquals(expectedTickets, actualTickets);
        verify(ticketRepository).findAll();
    }

    private Ticket createTicketWithSeatNumber(int seatNumber) {
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(seatNumber);
        return ticket;
    }
}
