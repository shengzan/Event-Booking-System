package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(Long[] ticketIds, User user);
    Order getOrderById(Long id);
    List<Order> getOrdersByUser(User user);
    boolean isUserAuthorizedForOrder(User user, Long orderId);
    List<Order> getAllOrders();
    Order updateOrderStatus(Long id, String status);
    Order cancelOrder(Long id);
    Order refundOrder(Long id);
    List<Order> getOrdersByStatus(String status);
}
