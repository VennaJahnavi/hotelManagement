package com.hotelManagment.service;

import com.hotelManagment.model.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KitchenLoadAnalyticsConsumer {

    private ConcurrentHashMap<Integer, List<Long>> chefLoad = new ConcurrentHashMap<>();

    @KafkaListener(topics = "order-events", groupId = "kitchen-load-group")
    public void consume(OrderEvent event) {

        long now = System.currentTimeMillis();
        if ("ORDER_ASSIGNED_CHEF".equals(event.getEventType())) {
            int chefId = event.getChefId();

            chefLoad.putIfAbsent(chefId, new ArrayList<>());
            chefLoad.get(chefId).add(now);
        }

        cleanupOldData();
    }

    private void cleanupOldData() {
        long cutoff = System.currentTimeMillis() - (15 * 60 * 1000);

        chefLoad.forEach((chef, timestamps) -> {
            timestamps.removeIf(ts -> ts < cutoff);
        });
    }

    public Map<String, Object> getKitchenLoadAnalytics() {

        int busiestChef = -1;
        int maxLoad = 0;

        for (var entry : chefLoad.entrySet()) {
            if (entry.getValue().size() > maxLoad) {
                maxLoad = entry.getValue().size();
                busiestChef = entry.getKey();
            }
        }

        return Map.of(
                "chefLoads", chefLoad,
                "busiestChef", busiestChef,
                "maxLoad", maxLoad
        );
    }
}
