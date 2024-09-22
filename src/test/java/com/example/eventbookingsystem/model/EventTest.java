package com.example.eventbookingsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    private Event event;
    private User organizer;

    @BeforeEach
    void setUp() {
        organizer = new User("organizer", "password", "organizer@example.com", User.UserRole.ORGANIZER);
        event = new Event("Test Event", "Description", new Date(), 100, "Test Location", 50.0, organizer);
    }

    @Test
    void testHasAvailableCapacity() {
        assertTrue(event.hasAvailableCapacity());
        
        // Reduce available capacity to 0
        for (int i = 0; i < 100; i++) {
            event.decreaseAvailableCapacity();
        }
        
        assertFalse(event.hasAvailableCapacity());
    }

    @Test
    void testDecreaseAvailableCapacity() {
        int initialCapacity = event.getAvailableCapacity();
        
        assertTrue(event.decreaseAvailableCapacity());
        assertEquals(initialCapacity - 1, event.getAvailableCapacity());
        
        // Reduce available capacity to 0
        for (int i = 1; i < initialCapacity; i++) {
            event.decreaseAvailableCapacity();
        }
        
        assertFalse(event.decreaseAvailableCapacity());
        assertEquals(0, event.getAvailableCapacity());
    }

    @Test
    void testToString() {
        String eventString = event.toString();
        
        assertTrue(eventString.contains("Test Event"));
        assertTrue(eventString.contains("Description"));
        assertTrue(eventString.contains("Test Location"));
        assertTrue(eventString.contains("50.0"));
    }

    @Test
    void testEquals() {
        Event sameEvent = new Event("Another Event", "Another Description", new Date(), 200, "Another Location", 75.0, organizer);
        sameEvent.setId(event.getId());
        
        Event differentEvent = new Event("Different Event", "Different Description", new Date(), 150, "Different Location", 60.0, organizer);
        
        assertEquals(event, sameEvent);
        assertNotEquals(event, differentEvent);
        assertNotEquals(event, null);
        assertNotEquals(event, new Object());
    }

    @Test
    void testHashCode() {
        Event sameEvent = new Event("Another Event", "Another Description", new Date(), 200, "Another Location", 75.0, organizer);
        sameEvent.setId(event.getId());
        
        assertEquals(event.hashCode(), sameEvent.hashCode());
    }
}
