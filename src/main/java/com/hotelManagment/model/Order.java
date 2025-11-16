package com.hotelManagment.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Order {
    private int id;
    private int customerId;
    private Integer chefId;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<OrderItem> items;
    private double totalAmount;
    private double subTotal;
    private double taxAmount;

}