package com.example.eventbookingsystem.dto;

public class EventOrder {
    private Long eventId;
    private int ticketCount;

    // Constructors
    public EventOrder() {}

    public EventOrder(Long eventId, int ticketCount) {
        this.eventId = eventId;
        this.ticketCount = ticketCount;
    }

    // Getters and setters
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
