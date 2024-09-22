package com.example.eventbookingsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;
    private User user;
    private Event event;

    @BeforeEach
    void setUp() {
        user = new User("testuser", "password", "test@example.com", User.UserRole.CUSTOMER);
        event = new Event("Test Event", "Description", java.sql.Date.valueOf("2023-12-31"), 100, "Test Location", 50.0, user);
        order = new Order(user, LocalDateTime.now(), Order.OrderStatus.PAID);
    }

    @Test
    void testAddTicket() {
        Ticket ticket = new Ticket(event, order, user, Ticket.TicketStatus.PAID);
        order.addTicket(ticket);

        assertTrue(order.getTickets().contains(ticket));
        assertEquals(order, ticket.getOrder());
    }

    @Test
    void testCalculateTotalPrice() {
        Ticket ticket1 = new Ticket(event, order, user, Ticket.TicketStatus.PAID);
        Ticket ticket2 = new Ticket(event, order, user, Ticket.TicketStatus.PAID);
        order.addTicket(ticket1);
        order.addTicket(ticket2);

        double expectedTotalPrice = event.getPrice() * 2;
        assertEquals(expectedTotalPrice, order.calculateTotalPrice(), 0.001);
    }

    @Test
    void testEqualsAndHashCode() {
        Order sameOrder = new Order(user, order.getOrderDate(), Order.OrderStatus.PAID);
        sameOrder.setId(order.getId());

        Order differentOrder = new Order(user, LocalDateTime.now(), Order.OrderStatus.PAID);
        differentOrder.setId(order.getId() + 1);

        assertEquals(order, sameOrder);
        assertNotEquals(order, differentOrder);
        assertEquals(order.hashCode(), sameOrder.hashCode());
        assertNotEquals(order.hashCode(), differentOrder.hashCode());
    }

    @Test
    void testToString() {
        String orderString = order.toString();

        assertTrue(orderString.contains("id=" + order.getId()));
        assertTrue(orderString.contains("user=" + user));
        assertTrue(orderString.contains("orderDate=" + order.getOrderDate()));
        assertTrue(orderString.contains("status=" + order.getStatus()));
        assertTrue(orderString.contains("tickets=" + order.getTickets()));
    }
}
