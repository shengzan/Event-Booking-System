package com.example.eventbookingsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    @NotNull(message = "Date is required")
    private Date date;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Location must be at most 255 characters")
    private String location;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double price;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    private int availableCapacity;

    public Event() {}

    public Event(String name, String description, Date date, int capacity, String location, double price, User organizer) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.capacity = capacity;
        this.availableCapacity = capacity;
        this.location = location;
        this.price = price;
        this.organizer = organizer;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public User getOrganizer() { return organizer; }
    public void setOrganizer(User organizer) { this.organizer = organizer; }
    public int getAvailableCapacity() { return availableCapacity; }
    public void setAvailableCapacity(int availableCapacity) { this.availableCapacity = availableCapacity; }

    public boolean hasAvailableCapacity() {
        return availableCapacity > 0;
    }

    public boolean decreaseAvailableCapacity() {
        if (availableCapacity > 0) {
            availableCapacity--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", capacity=" + capacity +
                ", availableCapacity=" + availableCapacity +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", organizer=" + organizer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
