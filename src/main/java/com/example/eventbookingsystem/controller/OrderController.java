package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.OrderService;
import com.example.eventbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Long[] ticketIds, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Order order = orderService.createOrder(ticketIds, user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (orderService.isUserAuthorizedForOrder(user, id)) {
            Order order = orderService.getOrderById(id);
            return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUserOrders(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        List<Order> orders = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (orderService.isUserAuthorizedForOrder(user, id)) {
            Order cancelledOrder = orderService.cancelOrder(id);
            return ResponseEntity.ok(cancelledOrder);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<?> refundOrder(@PathVariable Long id, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (orderService.isUserAuthorizedForOrder(user, id)) {
            Order refundedOrder = orderService.refundOrder(id);
            return ResponseEntity.ok(refundedOrder);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable Order.OrderStatus status, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (user.getRole() == User.UserRole.ADMIN) {
            List<Order> orders = orderService.getOrdersByStatus(status);
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.forbidden().build();
    }
}
