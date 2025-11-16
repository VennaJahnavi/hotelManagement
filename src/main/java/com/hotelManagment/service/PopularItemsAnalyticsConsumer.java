package com.hotelManagment.service;

import com.hotelManagment.model.OrderEvent;
import com.hotelManagment.model.OrderItem;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PopularItemsAnalyticsConsumer {

    private ConcurrentHashMap<Long, List<OrderItem>> last10MinOrders = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, List<OrderItem>> last60MinOrders = new ConcurrentHashMap<>();

    @KafkaListener(topics = "order-events", groupId = "popular-items-group")
    public void consumeOrderEvent(OrderEvent event) {

        if (!"ORDER_CREATED".equals(event.getEventType())) {
            return;
        }

        if (event.getItems() == null || event.getItems().isEmpty()) {
            return;
        }

        long now = System.currentTimeMillis();
        last10MinOrders.put(now, event.getItems());
        last60MinOrders.put(now, event.getItems());

        cleanupOldData();
    }

    private void cleanupOldData() {
        long now = System.currentTimeMillis();

        long tenMinAgo = now - (10 * 60 * 1000);
        long hourAgo = now - (60 * 60 * 1000);

        last10MinOrders.keySet().removeIf(ts -> ts < tenMinAgo);
        last60MinOrders.keySet().removeIf(ts -> ts < hourAgo);
    }

    public Map<String, Object> getPopularItemsAnalytics() {

        Map<String, Long> count10 = new HashMap<>();
        last10MinOrders.values().forEach(list -> {
            for (OrderItem item : list) {
                count10.merge(item.getName(), (long) item.getQuantity(), Long::sum);
            }
        });

        String topItem10Min = count10.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No data");


        Map<String, Long> categoryCount = new HashMap<>();
        last60MinOrders.values().forEach(list -> {
            for (OrderItem item : list) {
                categoryCount.merge(item.getName(), (long) item.getQuantity(), Long::sum);
            }
        });

        String topCategory = categoryCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No data");


        long now = System.currentTimeMillis();
        long currentHourStart = now - (30 * 60 * 1000);

        Map<String, Long> lastHourMap = new HashMap<>();
        Map<String, Long> currentHourMap = new HashMap<>();

        last60MinOrders.forEach((timestamp, items) -> {
            for (OrderItem item : items) {
                if (timestamp < currentHourStart) {
                    lastHourMap.merge(item.getName(), (long) item.getQuantity(), Long::sum);
                } else {
                    currentHourMap.merge(item.getName(), (long) item.getQuantity(), Long::sum);
                }
            }
        });

        Map<String, Long> trendingMap = new HashMap<>();
        currentHourMap.forEach((item, currCount) -> {
            long previous = lastHourMap.getOrDefault(item, 0L);
            trendingMap.put(item, currCount - previous);
        });

        String trendingItem = trendingMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No data");

        return Map.of(
                "mostOrderedLast10Min", topItem10Min,
                "fastestCategory", topCategory,
                "trendingItem", trendingItem
        );
    }

}

