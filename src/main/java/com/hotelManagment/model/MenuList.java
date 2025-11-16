package com.hotelManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuList {
    private int id;
    private String name;
    private String description;
    private double price;
    private boolean isAvailable;
    private String category;
    private String imageUrl;
}
