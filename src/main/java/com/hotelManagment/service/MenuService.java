package com.hotelManagment.service;

import com.hotelManagment.model.MenuList;
import com.hotelManagment.repository.MenuListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuListRepository menuListRepository;

    public List<MenuList> getAvailableMenuItems() {
        return menuListRepository.getAllAvailableMenuItems();
    }
}
