package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEvents() {
        // Arrange
        Event event1 = new Event();
        Event event2 = new Event();
        List<Event> expectedEvents = Arrays.asList(event1, event2);
        when(eventRepository.findAll()).thenReturn(expectedEvents);

        // Act
        List<Event> actualEvents = eventService.getAllEvents();

        // Assert
        assertEquals(expectedEvents, actualEvents);
        verify(eventRepository).findAll();
    }

    @Test
    void getEventById() {
        // Arrange
        Long eventId = 1L;
        Event expectedEvent = new Event();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(expectedEvent));

        // Act
        Event actualEvent = eventService.getEventById(eventId);

        // Assert
        assertEquals(expectedEvent, actualEvent);
        verify(eventRepository).findById(eventId);
    }

    @Test
    void addEvent() {
        // Arrange
        Event eventToAdd = new Event();
        when(eventRepository.save(eventToAdd)).thenReturn(eventToAdd);

        // Act
        Event addedEvent = eventService.addEvent(eventToAdd);

        // Assert
        assertEquals(eventToAdd, addedEvent);
        verify(eventRepository).save(eventToAdd);
    }

    @Test
    void updateEvent() {
        // Arrange
        Long eventId = 1L;
        Event eventToUpdate = new Event();
        eventToUpdate.setId(eventId);
        when(eventRepository.existsById(eventId)).thenReturn(true);
        when(eventRepository.save(eventToUpdate)).thenReturn(eventToUpdate);

        // Act
        Event updatedEvent = eventService.updateEvent(eventId, eventToUpdate);

        // Assert
        assertEquals(eventToUpdate, updatedEvent);
        verify(eventRepository).existsById(eventId);
        verify(eventRepository).save(eventToUpdate);
    }

    @Test
    void deleteEvent() {
        // Arrange
        Long eventId = 1L;

        // Act
        eventService.deleteEvent(eventId);

        // Assert
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    void getEventsByOrganizer() {
        // Arrange
        Long organizerId = 1L;
        Event event1 = new Event();
        Event event2 = new Event();
        List<Event> expectedEvents = Arrays.asList(event1, event2);
        when(eventRepository.findByOrganizerId(organizerId)).thenReturn(expectedEvents);

        // Act
        List<Event> actualEvents = eventService.getEventsByOrganizer(organizerId);

        // Assert
        assertEquals(expectedEvents, actualEvents);
        verify(eventRepository).findByOrganizerId(organizerId);
    }

    @Test
    void isUserEventOrganizer() {
        // Arrange
        Long userId = 1L;
        Long eventId = 1L;
        User organizer = new User();
        organizer.setId(userId);
        Event event = new Event();
        event.setOrganizer(organizer);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // Act
        boolean isOrganizer = eventService.isUserEventOrganizer(userId, eventId);

        // Assert
        assertTrue(isOrganizer);
        verify(eventRepository).findById(eventId);
    }
}
