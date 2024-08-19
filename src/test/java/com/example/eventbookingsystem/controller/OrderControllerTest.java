package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.dto.EventOrder;
import com.example.eventbookingsystem.dto.OrderRequest;
import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.service.OrderService;
import com.example.eventbookingsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private OrderController orderController;

    private User testUser;
    private Order testOrder;
    private OrderRequest testOrderRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setRole(User.UserRole.USER);

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setUser(testUser);
        testOrder.setStatus(Order.OrderStatus.CONFIRMED);

        testOrderRequest = new OrderRequest();
        testOrderRequest.setEventOrders(Arrays.asList(new EventOrder(1L, 2)));

        when(authentication.getName()).thenReturn("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(testUser);
    }

    @Test
    void createOrder() {
        when(orderService.createOrder(any(), any(User.class))).thenReturn(testOrder);

        ResponseEntity<?> response = orderController.createOrder(testOrderRequest, authentication);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testOrder, response.getBody());
    }

    @Test
    void getOrderById_Authorized() {
        when(orderService.isUserAuthorizedForOrder(testUser, 1L)).thenReturn(true);
        when(orderService.getOrderById(1L)).thenReturn(testOrder);

        ResponseEntity<?> response = orderController.getOrderById(1L, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testOrder, response.getBody());
    }

    @Test
    void getOrderById_Unauthorized() {
        when(orderService.isUserAuthorizedForOrder(testUser, 1L)).thenReturn(false);

        ResponseEntity<?> response = orderController.getOrderById(1L, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void getUserOrders() {
        List<Order> orders = Arrays.asList(testOrder);
        when(orderService.getOrdersByUser(testUser)).thenReturn(orders);

        ResponseEntity<?> response = orderController.getUserOrders(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void cancelOrder_Authorized() {
        when(orderService.isUserAuthorizedForOrder(testUser, 1L)).thenReturn(true);
        when(orderService.cancelOrder(1L)).thenReturn(testOrder);

        ResponseEntity<?> response = orderController.cancelOrder(1L, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testOrder, response.getBody());
    }

    @Test
    void cancelOrder_Unauthorized() {
        when(orderService.isUserAuthorizedForOrder(testUser, 1L)).thenReturn(false);

        ResponseEntity<?> response = orderController.cancelOrder(1L, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void refundOrder_AsAdmin() {
        testUser.setRole(User.UserRole.ADMIN);
        when(orderService.refundOrder(1L)).thenReturn(testOrder);

        ResponseEntity<?> response = orderController.refundOrder(1L, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testOrder, response.getBody());
    }

    @Test
    void refundOrder_AsUser() {
        ResponseEntity<?> response = orderController.refundOrder(1L, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void getOrdersByStatus_AsAdmin() {
        testUser.setRole(User.UserRole.ADMIN);
        List<Order> orders = Arrays.asList(testOrder);
        when(orderService.getOrdersByStatus(Order.OrderStatus.CONFIRMED)).thenReturn(orders);

        ResponseEntity<?> response = orderController.getOrdersByStatus(Order.OrderStatus.CONFIRMED, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void getOrdersByStatus_AsUser() {
        ResponseEntity<?> response = orderController.getOrdersByStatus(Order.OrderStatus.CONFIRMED, authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void getAllOrders_AsAdmin() {
        testUser.setRole(User.UserRole.ADMIN);
        List<Order> orders = Arrays.asList(testOrder);
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<?> response = orderController.getAllOrders(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void getAllOrders_AsUser() {
        ResponseEntity<?> response = orderController.getAllOrders(authentication);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
