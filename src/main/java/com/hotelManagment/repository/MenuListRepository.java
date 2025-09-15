package com.hotelManagment.repository;

import com.hotelManagment.model.MenuList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuListRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<MenuList> getAllAvailableMenuItems() {
        String sql = "SELECT * FROM menu_items WHERE is_available = 1";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new MenuList(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getInt("is_available") == 1
        ));
    }
}
