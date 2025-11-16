package com.hotelManagment.model;

import lombok.Data;

@Data
public class AddressRequest {
    private String fullName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String zipCode;
}