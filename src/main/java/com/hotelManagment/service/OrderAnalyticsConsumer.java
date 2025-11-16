package com.hotelManagment.service;

import com.hotelManagment.model.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;

@Service
public class OrderAnalyticsConsumer {

    private AtomicInteger totalOrdersToday = new AtomicInteger(0);
    private AtomicInteger ordersReady = new AtomicInteger(0);
    private AtomicInteger ordersPending = new AtomicInteger(0);
    private DoubleAdder totalRevenueToday = new DoubleAdder();

    @KafkaListener(topics = "order-events", groupId = "order-analytics-group")
    public void consumeOrderEvent(OrderEvent event) {

        String status = (event.getStatus() != null)
                ? event.getStatus().trim().toUpperCase()
                : "";

        if (event.getEventType().equals("ORDER_CREATED")) {
            totalOrdersToday.incrementAndGet();
            totalRevenueToday.add(event.getTotalAmount());
        }

        if (event.getEventType().equals("ORDER_STATUS_UPDATED")
                || event.getEventType().equals("ORDER_ASSIGNED_CHEF")) {

            if (status.equals("READY")) {
                ordersReady.incrementAndGet();
            }
            if (status.equals("CHEF_ASSIGNED")) {
                ordersPending.incrementAndGet();
            }
        }
    }

    public Map<String, Object> getAnalytics() {
        return Map.of(
                "totalOrdersToday", totalOrdersToday.get(),
                "ordersReady", ordersReady.get(),
                "ordersPending", ordersPending.get(),
                "totalRevenueToday", totalRevenueToday.doubleValue()
        );
    }
}
