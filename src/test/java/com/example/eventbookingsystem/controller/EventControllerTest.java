package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.EventService;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private EventController eventController;

    private User testUser;
    private Event testEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setRole(User.UserRole.USER);

        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setName("Test Event");
        testEvent.setDate(new Date());
        testEvent.setCapacity(100);
        testEvent.setLocation("Test Location");
        testEvent.setPrice(10.0);
        testEvent.setOrganizer(testUser);

        when(authentication.getName()).thenReturn("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(testUser);
    }

    @Test
    void getAllEvents() {
        List<Event> events = Arrays.asList(testEvent);
        when(eventService.getAllEvents()).thenReturn(events);

        ResponseEntity<?> response = eventController.getAllEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events, response.getBody());
    }

    @Test
    void getEventById() {
        when(eventService.getEventById(1L)).thenReturn(testEvent);

        ResponseEntity<?> response = eventController.getEventById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testEvent, response.getBody());
    }

    @Test
    void getEventById_NotFound() {
        when(eventService.getEventById(1L)).thenReturn(null);

        ResponseEntity<?> response = eventController.getEventById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createEvent() {
        when(eventService.addEvent(any(Event.class))).thenReturn(testEvent);

        ResponseEntity<?> response = eventController.addEvent(testEvent, authentication);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testEvent, response.getBody());
    }

    @Test
    void updateEvent_Authorized() {
        when(eventService.isUserEventOrganizer(1L, 1L)).thenReturn(true);
        when(eventService.getEventById(1L)).thenReturn(testEvent);
        when(eventService.updateEvent(eq(1L), any(Event.class))).thenReturn(testEvent);

        ResponseEntity<?> response = eventController.updateEvent(1L, testEvent, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testEvent, response.getBody());
        verify(eventService).getEventById(1L);
        verify(eventService).updateEvent(eq(1L), any(Event.class));
    }

    @Test
    void updateEvent_NotFound() {
        when(eventService.isUserEventOrganizer(1L, 1L)).thenReturn(true);
        when(eventService.getEventById(1L)).thenReturn(null);

        ResponseEntity<?> response = eventController.updateEvent(1L, testEvent, authentication);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(eventService).getEventById(1L);
        verify(eventService, never()).updateEvent(eq(1L), any(Event.class));
    }

    @Test
    void updateEvent_Unauthorized() {
        when(eventService.isUserEventOrganizer(1L, 1L)).thenReturn(false);

        ResponseEntity<?> response = eventController.updateEvent(1L, testEvent, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteEvent_Authorized() {
        when(eventService.isUserEventOrganizer(1L, 1L)).thenReturn(true);

        ResponseEntity<?> response = eventController.deleteEvent(1L, authentication);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(eventService, times(1)).deleteEvent(1L);
    }

    @Test
    void deleteEvent_Unauthorized() {
        when(eventService.isUserEventOrganizer(1L, 1L)).thenReturn(false);

        ResponseEntity<?> response = eventController.deleteEvent(1L, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(eventService, never()).deleteEvent(1L);
    }

    @Test
    void getEventsByOrganizer() {
        List<Event> events = Arrays.asList(testEvent);
        when(eventService.getEventsByOrganizer(1L)).thenReturn(events);

        ResponseEntity<?> response = eventController.getEventsByOrganizer(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events, response.getBody());
    }
}
