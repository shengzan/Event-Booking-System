package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event event, Authentication authentication) {
        // Assuming the authentication object contains the user's ID
        Long userId = Long.parseLong(authentication.getName());
        if (eventService.isUserEventOrganizer(userId, event.getId())) {
            return ResponseEntity.ok(eventService.addEvent(event));
        }
        return ResponseEntity.forbidden().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        if (eventService.isUserEventOrganizer(userId, id)) {
            Event updatedEvent = eventService.updateEvent(id, event);
            return updatedEvent != null ? ResponseEntity.ok(updatedEvent) : ResponseEntity.notFound().build();
        }
        return ResponseEntity.forbidden().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        if (eventService.isUserEventOrganizer(userId, id)) {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.forbidden().build();
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<Event>> getEventsByOrganizer(@PathVariable Long organizerId) {
        return ResponseEntity.ok(eventService.getEventsByOrganizer(organizerId));
    }
}
