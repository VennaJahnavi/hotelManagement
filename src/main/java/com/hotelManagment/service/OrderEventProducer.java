package com.hotelManagment.service;

import com.hotelManagment.model.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEvent(OrderEvent event) {
        kafkaTemplate.send("order-events", event);
    }
}

