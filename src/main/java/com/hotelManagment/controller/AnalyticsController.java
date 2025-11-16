package com.hotelManagment.controller;

import com.hotelManagment.service.KitchenLoadAnalyticsConsumer;
import com.hotelManagment.service.OrderAnalyticsConsumer;
import com.hotelManagment.service.PopularItemsAnalyticsConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private OrderAnalyticsConsumer analytics;

    @Autowired
    private PopularItemsAnalyticsConsumer popularItemsService;

    @Autowired
    private KitchenLoadAnalyticsConsumer kitchenLoadAnalyticsConsumer;

    @GetMapping("/popular-items")
    public Map<String, Object> getPopularItems() {
        return popularItemsService.getPopularItemsAnalytics();
    }

    @GetMapping("/live")
    public Map<String, Object> getLiveAnalytics() {
        return analytics.getAnalytics();
    }

    @GetMapping("/kitchen-load")
    public Map<String, Object> getKitchenLoad() {
        return kitchenLoadAnalyticsConsumer.getKitchenLoadAnalytics();
    }
}

