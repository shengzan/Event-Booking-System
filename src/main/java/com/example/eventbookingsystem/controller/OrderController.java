package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.dto.EventOrder;
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

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody List<EventOrder> eventOrders, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            Order order = orderService.createOrder(eventOrders, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating order: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (orderService.isUserAuthorizedForOrder(user, id) || user.getRole() == User.UserRole.ADMIN) {
                Order order = orderService.getOrderById(id);
                return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to view this order");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving order: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserOrders(Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            List<Order> orders = orderService.getOrdersByUser(user);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving user orders: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (orderService.isUserAuthorizedForOrder(user, id) || user.getRole() == User.UserRole.ADMIN) {
                Order cancelledOrder = orderService.cancelOrder(id);
                return ResponseEntity.ok(cancelledOrder);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to cancel this order");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error cancelling order: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<?> refundOrder(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (user.getRole() == User.UserRole.ADMIN) {
                Order refundedOrder = orderService.refundOrder(id);
                return ResponseEntity.ok(refundedOrder);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only administrators can refund orders");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error refunding order: " + e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable Order.OrderStatus status, Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (user.getRole() == User.UserRole.ADMIN) {
                List<Order> orders = orderService.getOrdersByStatus(status);
                return ResponseEntity.ok(orders);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only administrators can view orders by status");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving orders by status: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            if (user.getRole() == User.UserRole.ADMIN) {
                List<Order> orders = orderService.getAllOrders();
                return ResponseEntity.ok(orders);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only administrators can view all orders");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving all orders: " + e.getMessage());
        }
    }
}
