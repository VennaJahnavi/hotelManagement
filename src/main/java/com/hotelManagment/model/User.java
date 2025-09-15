package com.hotelManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Role role;
    private Timestamp createdAt;

    public enum Role {
        CUSTOMER, MANAGER, CHEF
    }
}
