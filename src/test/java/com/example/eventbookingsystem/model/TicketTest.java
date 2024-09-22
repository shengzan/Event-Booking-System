package com.example.eventbookingsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    private Ticket ticket;
    private Event event;
    private Order order;
    private User user;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setPrice(50.0);

        order = new Order();
        order.setId(1L);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        ticket = new Ticket(event, order, user, Ticket.TicketStatus.PAID);
        ticket.setId(1L);
        ticket.setSeatNumber(10);
    }

    @Test
    void testTicketCreation() {
        assertNotNull(ticket);
        assertEquals(1L, ticket.getId());
        assertEquals(event, ticket.getEvent());
        assertEquals(order, ticket.getOrder());
        assertEquals(user, ticket.getUser());
        assertEquals(Ticket.TicketStatus.PAID, ticket.getStatus());
        assertEquals(10, ticket.getSeatNumber());
        assertEquals(50.0, ticket.getTicketPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        Ticket sameTicket = new Ticket(event, order, user, Ticket.TicketStatus.PAID);
        sameTicket.setId(1L);

        Ticket differentTicket = new Ticket(event, order, user, Ticket.TicketStatus.PAID);
        differentTicket.setId(2L);

        assertEquals(ticket, sameTicket);
        assertNotEquals(ticket, differentTicket);
        assertEquals(ticket.hashCode(), sameTicket.hashCode());
        assertNotEquals(ticket.hashCode(), differentTicket.hashCode());
    }

    @Test
    void testToString() {
        String ticketString = ticket.toString();
        assertTrue(ticketString.contains("id=1"));
        assertTrue(ticketString.contains("status=PAID"));
        assertTrue(ticketString.contains("seatNumber=10"));
    }

    @Test
    void testSetAndGetStatus() {
        ticket.setStatus(Ticket.TicketStatus.CANCELED);
        assertEquals(Ticket.TicketStatus.CANCELED, ticket.getStatus());
    }

    @Test
    void testSetAndGetEvent() {
        Event newEvent = new Event();
        newEvent.setId(2L);
        ticket.setEvent(newEvent);
        assertEquals(newEvent, ticket.getEvent());
    }

    @Test
    void testSetAndGetOrder() {
        Order newOrder = new Order();
        newOrder.setId(2L);
        ticket.setOrder(newOrder);
        assertEquals(newOrder, ticket.getOrder());
    }

    @Test
    void testSetAndGetUser() {
        User newUser = new User();
        newUser.setId(2L);
        ticket.setUser(newUser);
        assertEquals(newUser, ticket.getUser());
    }
}
