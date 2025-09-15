package com.hotelManagment.repository;

import com.hotelManagment.model.Order;
import com.hotelManagment.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertOrder(int customerId) {
        String sql = "INSERT INTO orders (customer_id, status, created_at, updated_at) VALUES (?, 'PENDING', NOW(), NOW())";
        jdbcTemplate.update(sql, customerId);
        // Retrieve generated order ID
        Integer orderId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return orderId;
    }

    public void insertOrderItem(int orderId, OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, menu_item_id, quantity, special_notes) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderId, item.getMenuItemId(), item.getQuantity(), item.getSpecialNotes());
    }

    public List<Order> findOrdersByCustomer(int customerId) {
        String sql = "SELECT * FROM orders WHERE customer_id = ?";
        List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper(), customerId);
        orders.forEach(this::populateOrderItems);
        return orders;
    }

    public List<Order> findAllOrders() {
        String sql = "SELECT * FROM orders";
        List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper());
        orders.forEach(this::populateOrderItems);
        return orders;
    }

    private void populateOrderItems(Order order) {
        String sql = """
        SELECT oi.menu_item_id, oi.quantity, oi.special_notes, mi.name, mi.price
        FROM order_items oi
        JOIN menu_items mi ON oi.menu_item_id = mi.id
        WHERE oi.order_id = ?
    """;

        List<OrderItem> items = jdbcTemplate.query(sql, (rs, rowNum) -> {
            OrderItem item = new OrderItem();
            item.setMenuItemId(rs.getInt("menu_item_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setSpecialNotes(rs.getString("special_notes"));
            item.setName(rs.getString("name"));
            item.setPrice(rs.getDouble("price")); // Make sure OrderItem has this field
            return item;
        }, order.getId());

        order.setItems(items);

        // Calculate total
        double total = items.stream()
                .mapToDouble(i -> i.getQuantity() * i.getPrice())
                .sum();
        order.setTotalAmount(total);  // 👈 Set total
    }

    // Assign chef to order
    public boolean assignChef(Integer orderId, Integer chefId) {
        String sql = "UPDATE orders SET chef_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, chefId, orderId) > 0;
    }

    // Update order status
    public void updateOrderStatus(Integer orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, orderId);
    }

    public List<Order> findOrdersByChef(int chefId) {
        String sql = "SELECT * FROM orders WHERE chef_id = ?";
        List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper(), chefId);
        orders.forEach(this::populateOrderItems);
        return orders;
    }
}

class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setCustomerId(rs.getInt("customer_id"));
        order.setChefId(rs.getObject("chef_id") != null ? rs.getInt("chef_id") : null);
        order.setStatus(rs.getString("status"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        order.setUpdatedAt(rs.getTimestamp("updated_at"));
        return order;
    }
}
