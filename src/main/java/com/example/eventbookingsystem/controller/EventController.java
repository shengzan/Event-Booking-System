package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        // TODO: Implement getAllEvents
        return null;
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        // TODO: Implement getEventById
        return null;
    }

    @PostMapping
    public Event addEvent(@RequestBody Event event) {
        // TODO: Implement addEvent
        // TODO: Validate that the user is an Event Organizer
        return null;
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        // TODO: Implement updateEvent
        // TODO: Validate that the user is the Event Organizer of this event
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        // TODO: Implement deleteEvent
        // TODO: Validate that the user is the Event Organizer of this event
    }

    @GetMapping("/organizer/{organizerId}")
    public List<Event> getEventsByOrganizer(@PathVariable Long organizerId) {
        // TODO: Implement getEventsByOrganizer
        // TODO: Validate that the user is authorized to view these events
        return null;
    }
}
