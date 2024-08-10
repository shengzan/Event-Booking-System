package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Event;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    Event getEventById(Long id);
    Event addEvent(Event event);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
    List<Event> getEventsByOrganizer(Long organizerId);
    // TODO: Implement method to get events by organizer
    // TODO: Implement method to check if a user is the organizer of an event
}
