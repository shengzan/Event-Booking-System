package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.EventService;
import com.example.eventbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        try {
            List<Event> events = eventService.getAllEvents();
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addEvent(@RequestBody @Valid Event event, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (user.getRole() == User.UserRole.ORGANIZER || user.getRole() == User.UserRole.ADMIN) {
                event.setOrganizer(user);
                Event createdEvent = eventService.addEvent(event);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only organizers and admins can create events");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating event: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody @Valid Event event, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (eventService.isUserEventOrganizer(user.getId(), id) || user.getRole() == User.UserRole.ADMIN) {
                Event updatedEvent = eventService.updateEvent(id, event);
                if (updatedEvent == null) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(updatedEvent);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to update this event");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating event: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (eventService.isUserEventOrganizer(user.getId(), id) || user.getRole() == User.UserRole.ADMIN) {
                if (eventService.getEventById(id) == null) {
                    return ResponseEntity.notFound().build();
                }
                eventService.deleteEvent(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to delete this event");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting event: " + e.getMessage());
        }
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<Event>> getEventsByOrganizer(@PathVariable Long organizerId) {
        return ResponseEntity.ok(eventService.getEventsByOrganizer(organizerId));
    }
}
