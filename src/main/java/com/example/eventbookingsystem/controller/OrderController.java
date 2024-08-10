package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Long[] ticketIds, @RequestParam String customerName, @RequestParam String customerEmail) {
        // TODO: Implement createOrder
        return null;
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        // TODO: Implement getOrderById
        return null;
    }
}
