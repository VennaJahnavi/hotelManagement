package com.hotelManagment.model;


import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private int customerId;
    private List<OrderItem> items;
}