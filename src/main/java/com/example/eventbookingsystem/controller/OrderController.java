package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Long[] ticketIds, Authentication authentication) {
        // TODO: Implement createOrder
        // TODO: Get user from authentication
        // TODO: Create order using orderService
        // TODO: Return ResponseEntity with created order and appropriate status
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id, Authentication authentication) {
        // TODO: Implement getOrderById
        // TODO: Get user from authentication
        // TODO: Check if user is authorized to view this order
        // TODO: Get order using orderService
        // TODO: Return ResponseEntity with order and appropriate status
        return null;
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUserOrders(Authentication authentication) {
        // TODO: Implement getUserOrders
        // TODO: Get user from authentication
        // TODO: Get user's orders using orderService
        // TODO: Return ResponseEntity with list of orders and appropriate status
        return null;
    }
}
