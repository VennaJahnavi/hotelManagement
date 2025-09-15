package com.hotelManagment.model;

import lombok.Data;

@Data
public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer menuItemId;
    private String name;
    private Integer quantity;
    private String specialNotes;
    private double price;
}
