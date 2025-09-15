package com.hotelManagment.controller;

import com.hotelManagment.model.MenuList;
import com.hotelManagment.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<MenuList> getAvailableMenuItems() {
        return menuService.getAvailableMenuItems();
    }
}
