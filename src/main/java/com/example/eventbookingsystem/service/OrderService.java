package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(Long[] ticketIds, User user);
    Order getOrderById(Long id);
    List<Order> getOrdersByUser(User user);
    boolean isUserAuthorizedForOrder(User user, Long orderId);
    
    // TODO: Implement method to update order status
    // TODO: Implement method to cancel an order
    // TODO: Implement method to refund an order
    // TODO: Implement method to get orders by status
}
