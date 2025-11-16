package com.hotelManagment.repository;

import com.hotelManagment.model.Order;
import com.hotelManagment.model.OrderItem;
import com.hotelManagment.model.SalesItem;
import com.hotelManagment.model.SalesReport;
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

    public int insertOrder(int customerId, double subTotal, double taxAmount, double totalAmount,
                           String fullName, String phone, String line1, String line2, String city, String zip) {

        String sql = """
                INSERT INTO orders 
                (customer_id, sub_total, tax_amount, total_amount, status,
                 address_full_name, address_phone, address_line1, address_line2, address_city, address_zip,
                 created_at, updated_at)
                VALUES (?, ?, ?, ?, 'OPEN', ?, ?, ?, ?, ?, ?, NOW(), NOW())
                """;

        jdbcTemplate.update(sql,
                customerId,
                subTotal,
                taxAmount,
                totalAmount,

                fullName,
                phone,
                line1,
                line2,
                city,
                zip
        );

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }


    public void insertOrderItem(int orderId, OrderItem item) {
        String sql = """
                    INSERT INTO order_items
                    (order_id, menu_item_id, quantity, special_notes, price, line_total)
                    VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                orderId,
                item.getMenuItemId(),
                item.getQuantity(),
                item.getSpecialNotes(),
                item.getPrice(),
                item.getLineTotal()
        );
    }


    public List<Order> findOrdersByCustomer(int customerId) {
        String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY created_at DESC";
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
                    SELECT oi.menu_item_id, oi.quantity, oi.special_notes, 
                           mi.name, mi.price, mi.image_url
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
            item.setPrice(rs.getDouble("price"));
            item.setImageUrl(rs.getString("image_url"));
            return item;
        }, order.getId());

        order.setItems(items);
    }

    public boolean assignChef(Integer orderId, Integer chefId) {
        String sql = "UPDATE orders SET chef_id = ?, status = 'CHEF_ASSIGNED' WHERE id = ?";
        return jdbcTemplate.update(sql, chefId, orderId) > 0;
    }

    public void updateOrderStatus(Integer orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, orderId);
    }

    public List<Order> findOrdersByChef(int chefId) {
        String sql = "SELECT * FROM orders WHERE chef_id = ? AND status <> 'READY'";
        List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper(), chefId);
        orders.forEach(this::populateOrderItems);
        return orders;
    }

    public Order findOrderById(Integer orderId) {
        String sql = "SELECT * FROM orders WHERE id = ?";

        List<Order> list = jdbcTemplate.query(sql, new OrderRowMapper(), orderId);

        if (list.isEmpty()) return null;

        Order order = list.get(0);
        populateOrderItems(order);

        return order;
    }

    public SalesReport getSalesSummary(String to) {

        String sql = """
                SELECT COUNT(DISTINCT o.id) AS total_orders,
                       SUM(oi.quantity * mi.price) AS total_revenue
                FROM orders o
                JOIN order_items oi ON o.id = oi.order_id
                JOIN menu_items mi ON oi.menu_item_id = mi.id
                WHERE DATE(o.created_at) <= ?  -- Orders before the 'to' date
                AND o.status = 'READY'  -- Only include orders that are READY
                """;

        return jdbcTemplate.query(sql, rs -> {
            SalesReport report = new SalesReport();
            if (rs.next()) {
                report.setTotalOrders(rs.getInt("total_orders"));
                report.setTotalRevenue(rs.getDouble("total_revenue"));
            }
            return report;
        }, to);
    }

    public List<SalesItem> getSalesItemBreakdown(String to) {

        String sql = """
                SELECT mi.name AS item_name,
                       SUM(oi.quantity) AS qty,
                       SUM(oi.quantity * mi.price) AS amount
                FROM order_items oi
                JOIN menu_items mi ON oi.menu_item_id = mi.id
                JOIN orders o ON o.id = oi.order_id
                WHERE DATE(o.created_at) <= ?  -- Orders before the 'to' date
                AND o.status = 'READY'  -- Only include orders that are READY
                GROUP BY mi.name
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SalesItem item = new SalesItem();
            item.setItemName(rs.getString("item_name"));
            item.setQuantitySold(rs.getInt("qty"));
            item.setTotalAmount(rs.getDouble("amount"));
            return item;
        }, to);
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
        order.setSubTotal(rs.getDouble("sub_total"));
        order.setTaxAmount(rs.getDouble("tax_amount"));
        order.setTotalAmount(rs.getDouble("total_amount"));

        return order;
    }
}

