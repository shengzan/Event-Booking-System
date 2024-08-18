package com.example.eventbookingsystem.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Integer seatNumber;
    
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    public enum TicketStatus {
        ACTIVE, CANCELLED, REFUNDED
    }

    public Ticket() {}

    public Ticket(Event event, Order order, TicketStatus status) {
        this.event = event;
        this.order = order;
        this.status = status;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public double getTicketPrice() {
        return event != null ? event.getPrice() : 0.0;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", event=" + event +
                ", user=" + user +
                ", order=" + order +
                ", seatNumber=" + seatNumber +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
