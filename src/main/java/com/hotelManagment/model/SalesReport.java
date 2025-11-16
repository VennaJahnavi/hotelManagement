package com.hotelManagment.model;

import java.util.List;

public class SalesReport {
    private double totalRevenue;
    private int totalOrders;
    private List<SalesItem> items;

    public double getTotalRevenue() {
        return totalRevenue;
    }
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    public int getTotalOrders() {
        return totalOrders;
    }
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
    public List<SalesItem> getItems() {
        return items;
    }
    public void setItems(List<SalesItem> items) {
        this.items = items;
    }
}
