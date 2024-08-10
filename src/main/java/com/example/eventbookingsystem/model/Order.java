package com.example.eventbookingsystem.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public enum OrderStatus {
        PAID, REFUNDED, CANCELLED
    }

    // Constructors
    public Order() {}

    public Order(User user, LocalDateTime orderDate, OrderStatus status) {
        this.user = user;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    // Helper methods
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setOrder(this);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setOrder(null);
    }

    public double calculateTotalPrice() {
        return tickets.stream().mapToDouble(Ticket::getTicketPrice).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", tickets=" + tickets +
                '}';
    }
}
