package com.example.eventbookingsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testuser", "password123", "test@example.com", User.UserRole.CUSTOMER);
    }

    @Test
    void testAddOrder() {
        Order order = new Order();
        user.addOrder(order);

        assertTrue(user.getOrders().contains(order));
        assertEquals(user, order.getUser());
    }

    @Test
    void testRemoveOrder() {
        Order order = new Order();
        user.addOrder(order);
        user.removeOrder(order);

        assertFalse(user.getOrders().contains(order));
        assertNull(order.getUser());
    }

    @Test
    void testAddOrganizedEvent() {
        Event event = new Event();
        user.addOrganizedEvent(event);

        assertTrue(user.getOrganizedEvents().contains(event));
        assertEquals(user, event.getOrganizer());
    }

    @Test
    void testRemoveOrganizedEvent() {
        Event event = new Event();
        user.addOrganizedEvent(event);
        user.removeOrganizedEvent(event);

        assertFalse(user.getOrganizedEvents().contains(event));
        assertNull(event.getOrganizer());
    }

    @Test
    void testIsEventOrganizer() {
        user.setRole(User.UserRole.CUSTOMER);
        assertFalse(user.isEventOrganizer());

        user.setRole(User.UserRole.ORGANIZER);
        assertTrue(user.isEventOrganizer());
    }

    @Test
    void testEqualsAndHashCode() {
        User sameUser = new User("testuser", "password123", "test@example.com", User.UserRole.CUSTOMER);
        sameUser.setId(user.getId());

        User differentUser = new User("otheruser", "password456", "other@example.com", User.UserRole.CUSTOMER);

        assertEquals(user, sameUser);
        assertNotEquals(user, differentUser);
        assertEquals(user.hashCode(), sameUser.hashCode());
        assertNotEquals(user.hashCode(), differentUser.hashCode());
    }

    @Test
    void testToString() {
        String userString = user.toString();

        assertTrue(userString.contains(user.getUsername()));
        assertTrue(userString.contains(user.getEmail()));
        assertTrue(userString.contains(user.getRole().toString()));
        assertFalse(userString.contains(user.getPassword())); // Ensure password is not included in toString
    }
}
