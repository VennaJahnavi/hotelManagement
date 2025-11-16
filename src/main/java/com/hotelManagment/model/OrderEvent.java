package com.hotelManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String eventType;
    private Integer orderId;
    private Integer customerId;
    private Integer chefId;
    private String status;
    private Double totalAmount;
    private long timestamp;
    private List<OrderItem> items;

}
