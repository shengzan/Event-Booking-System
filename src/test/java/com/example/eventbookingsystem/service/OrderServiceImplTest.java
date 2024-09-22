package com.example.eventbookingsystem.service;

import com.example.eventbookingsystem.dto.EventOrder;
import com.example.eventbookingsystem.model.Event;
import com.example.eventbookingsystem.model.Order;
import com.example.eventbookingsystem.model.Ticket;
import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.repository.OrderRepository;
import com.example.eventbookingsystem.repository.TicketRepository;
import com.example.eventbookingsystem.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Event event = new Event();
        event.setId(1L);
        EventOrder eventOrder = new EventOrder(1L, 2);
        List<EventOrder> eventOrders = Arrays.asList(eventOrder);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(ticketService.createTicket(any(), any(), any())).thenReturn(new Ticket());
        when(orderRepository.save(any())).thenReturn(new Order());

        // Act
        Order createdOrder = orderService.createOrder(eventOrders, user);

        // Assert
        assertNotNull(createdOrder);
        verify(orderRepository, times(2)).save(any());
        verify(ticketRepository, times(2)).save(any());
        verify(eventRepository).findById(1L);
        verify(ticketService, times(2)).createTicket(any(), any(), any());
    }

    @Test
    void getOrderById() {
        // Arrange
        Long orderId = 1L;
        Order expectedOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        // Act
        Order actualOrder = orderService.getOrderById(orderId);

        // Assert
        assertEquals(expectedOrder, actualOrder);
        verify(orderRepository).findById(orderId);
    }

    @Test
    void getOrdersByUser() {
        // Arrange
        User user = new User();
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findByUser(user)).thenReturn(expectedOrders);

        // Act
        List<Order> actualOrders = orderService.getOrdersByUser(user);

        // Assert
        assertEquals(expectedOrders, actualOrders);
        verify(orderRepository).findByUser(user);
    }

    @Test
    void isUserAuthorizedForOrder() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Long orderId = 1L;
        Order order = new Order();
        order.setUser(user);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        boolean isAuthorized = orderService.isUserAuthorizedForOrder(user, orderId);

        // Assert
        assertTrue(isAuthorized);
        verify(orderRepository).findById(orderId);
    }

    @Test
    void getAllOrders() {
        // Arrange
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAll()).thenReturn(expectedOrders);

        // Act
        List<Order> actualOrders = orderService.getAllOrders();

        // Assert
        assertEquals(expectedOrders, actualOrders);
        verify(orderRepository).findAll();
    }

    @Test
    void updateOrderStatus() {
        // Arrange
        Long orderId = 1L;
        String newStatus = "PAID";
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenReturn(order);

        // Act
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);

        // Assert
        assertNotNull(updatedOrder);
        assertEquals(Order.OrderStatus.PAID, updatedOrder.getStatus());
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(order);
    }

    @Test
    void cancelOrder() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        Ticket ticket = new Ticket();
        order.addTicket(ticket);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenReturn(order);

        // Act
        Order canceledOrder = orderService.cancelOrder(orderId);

        // Assert
        assertNotNull(canceledOrder);
        assertEquals(Order.OrderStatus.CANCELED, canceledOrder.getStatus());
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(order);
        verify(ticketService).updateTicketStatus(any(), eq(Ticket.TicketStatus.CANCELED));
    }

    @Test
    void refundOrder() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenReturn(order);

        // Act
        Order refundedOrder = orderService.refundOrder(orderId);

        // Assert
        assertNotNull(refundedOrder);
        assertEquals(Order.OrderStatus.REFUNDED, refundedOrder.getStatus());
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(order);
    }

    @Test
    void getOrdersByStatus() {
        // Arrange
        Order.OrderStatus status = Order.OrderStatus.PAID;
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findByStatus(status)).thenReturn(expectedOrders);

        // Act
        List<Order> actualOrders = orderService.getOrdersByStatus(status);

        // Assert
        assertEquals(expectedOrders, actualOrders);
        verify(orderRepository).findByStatus(status);
    }
}
