package com.example.eventbookingsystem.dto;

import java.util.List;

public class OrderRequest {
    private List<EventOrder> eventOrders;

    public List<EventOrder> getEventOrders() {
        return eventOrders;
    }

    public void setEventOrders(List<EventOrder> eventOrders) {
        this.eventOrders = eventOrders;
    }
}
