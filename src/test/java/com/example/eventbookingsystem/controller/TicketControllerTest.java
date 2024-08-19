package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.EventService;
import com.example.eventbookingsystem.service.TicketService;
import com.example.eventbookingsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @Mock
    private UserService userService;

    @Mock
    private EventService eventService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TicketController ticketController;

    private User testUser;
    private Ticket testTicket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setRole(User.UserRole.CUSTOMER);

        testTicket = new Ticket();
        testTicket.setId(1L);
        testTicket.setUser(testUser);
        testTicket.setStatus(Ticket.TicketStatus.PAID);

        when(authentication.getName()).thenReturn("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(testUser);
    }

    @Test
    void getTicketById_Authorized() {
        when(ticketService.getTicketById(1L)).thenReturn(testTicket);

        ResponseEntity<?> response = ticketController.getTicketById(1L, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testTicket, response.getBody());
    }

    @Test
    void getTicketById_Unauthorized() {
        User otherUser = new User();
        otherUser.setId(2L);
        testTicket.setUser(otherUser);
        when(ticketService.getTicketById(1L)).thenReturn(testTicket);

        ResponseEntity<?> response = ticketController.getTicketById(1L, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void cancelTicket_Authorized() {
        when(ticketService.getTicketById(1L)).thenReturn(testTicket);
        when(ticketService.updateTicketStatus(1L, Ticket.TicketStatus.CANCELED)).thenReturn(testTicket);

        ResponseEntity<?> response = ticketController.cancelTicket(1L, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testTicket, response.getBody());
    }

    @Test
    void cancelTicket_Unauthorized() {
        User otherUser = new User();
        otherUser.setId(2L);
        testTicket.setUser(otherUser);
        when(ticketService.getTicketById(1L)).thenReturn(testTicket);

        ResponseEntity<?> response = ticketController.cancelTicket(1L, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void updateTicketStatus_AsAdmin() {
        testUser.setRole(User.UserRole.ADMIN);
        when(ticketService.updateTicketStatus(1L, Ticket.TicketStatus.CANCELED)).thenReturn(testTicket);

        ResponseEntity<?> response = ticketController.updateTicketStatus(1L, Ticket.TicketStatus.CANCELED, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testTicket, response.getBody());
    }

    @Test
    void updateTicketStatus_AsUser() {
        ResponseEntity<?> response = ticketController.updateTicketStatus(1L, Ticket.TicketStatus.CANCELED, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void getTicketsByEvent_AsAdmin() {
        testUser.setRole(User.UserRole.ADMIN);
        List<Ticket> tickets = Arrays.asList(testTicket);
        when(ticketService.getTicketsByEvent(1L)).thenReturn(tickets);

        ResponseEntity<?> response = ticketController.getTicketsByEvent(1L, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void getTicketsByEvent_AsUser() {
        ResponseEntity<?> response = ticketController.getTicketsByEvent(1L, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void getTicketsByUser() {
        List<Ticket> tickets = Arrays.asList(testTicket);
        when(ticketService.getTicketsByUser(1L)).thenReturn(tickets);

        ResponseEntity<?> response = ticketController.getTicketsByUser(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }
}
