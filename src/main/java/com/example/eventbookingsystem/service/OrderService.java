package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.model.Order;

public interface OrderService {
    Order createOrder(Long[] ticketIds, String customerName, String customerEmail);
    Order getOrderById(Long id);
}
