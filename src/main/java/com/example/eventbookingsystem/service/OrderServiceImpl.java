package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.dto.EventOrder;
import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.repository.OrderRepository;
import com.example.eventbookingsystem.repository.TicketRepository;
import com.example.eventbookingsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, TicketRepository ticketRepository, EventRepository eventRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public Order createOrder(List<EventOrder> eventOrders, User user) {
        Order order = new Order(user, LocalDateTime.now(), Order.OrderStatus.PAID);
    
        for (EventOrder eventOrder : eventOrders) {
            Event event = eventRepository.findById(eventOrder.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
            for (int i = 0; i < eventOrder.getTicketCount(); i++) {
                Ticket ticket = new Ticket(event, order, user, Ticket.TicketStatus.ACTIVE);
                ticket = ticketRepository.save(ticket);
                order.addTicket(ticket);
            }
        }
    
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public boolean isUserAuthorizedForOrder(User user, Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(o -> o.getUser().getId().equals(user.getId())).orElse(false);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found");
    }

    @Override
    @Transactional
    public Order cancelOrder(Long id) {
        return updateOrderStatus(id, "CANCELLED");
    }

    @Override
    @Transactional
    public Order refundOrder(Long id) {
        return updateOrderStatus(id, "REFUNDED");
    }

    @Override
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
}
