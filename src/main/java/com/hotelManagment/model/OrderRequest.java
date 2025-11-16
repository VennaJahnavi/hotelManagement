package com.hotelManagment.model;


import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private int customerId;
    private List<OrderItem> items;
    private double subTotal;
    private double taxAmount;
    private double totalAmount;
    private AddressRequest address;
}