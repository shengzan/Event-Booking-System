package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Long[] ticketIds, User user) {
        // Implementation for creating an order
        // This would involve creating a new Order object, associating it with tickets and user, and saving it
        return null; // Placeholder return
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
    public Order updateOrderStatus(Long id, String status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public Order cancelOrder(Long id) {
        return updateOrderStatus(id, "CANCELLED");
    }

    @Override
    public Order refundOrder(Long id) {
        return updateOrderStatus(id, "REFUNDED");
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}
