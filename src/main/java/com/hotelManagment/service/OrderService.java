package com.hotelManagment.service;

import com.hotelManagment.model.*;
import com.hotelManagment.repository.OrderRepository;
import com.hotelManagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventProducer orderEventProducer;

    @Transactional
    public void placeOrder(OrderRequest request) {

        AddressRequest addr = request.getAddress();

        int orderId = orderRepository.insertOrder(
                request.getCustomerId(),
                request.getSubTotal(),
                request.getTaxAmount(),
                request.getTotalAmount(),

                addr.getFullName(),
                addr.getPhone(),
                addr.getAddressLine1(),
                addr.getAddressLine2(),
                addr.getCity(),
                addr.getZipCode()
        );

        for (OrderItem item : request.getItems()) {
            orderRepository.insertOrderItem(orderId, item);
        }

        OrderEvent event = new OrderEvent(
                "ORDER_CREATED",
                orderId,
                request.getCustomerId(),
                null,
                "OPEN",
                request.getTotalAmount(),
                System.currentTimeMillis(),
                request.getItems()
        );

        orderEventProducer.sendEvent(event);
    }


    public List<Order> getOrdersByCustomer(int customerId) {
        return orderRepository.findOrdersByCustomer(customerId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }

    public boolean assignChef(Integer orderId, Integer chefId, Integer managerId) {

        Optional<User> manager = userRepository.findById(managerId);
        if (manager.isPresent() && manager.get().getRole() == User.Role.MANAGER) {

            boolean assigned = orderRepository.assignChef(orderId, chefId);

            if (assigned) {
                OrderEvent event = new OrderEvent(
                        "ORDER_ASSIGNED_CHEF",
                        orderId,
                        null,
                        chefId,
                        "CHEF_ASSIGNED",
                        null,
                        System.currentTimeMillis(),
                        null
                );
                orderEventProducer.sendEvent(event);
            }

            return assigned;
        }
        return false;
    }


    public void updateStatus(Integer orderId, String status) {
        orderRepository.updateOrderStatus(orderId, status);
        OrderEvent event = new OrderEvent(
                "ORDER_STATUS_UPDATED",
                orderId,
                null,
                null,
                status,
                null,
                System.currentTimeMillis(),
                null
        );

        orderEventProducer.sendEvent(event);
    }

    public List<Order> findByChef(Integer chefId) {
        return orderRepository.findOrdersByChef(chefId);
    }

    public boolean cancelOrder(Integer orderId) {
        Order order = orderRepository.findOrderById(orderId);

        if (order == null) return false;

        if (!"OPEN".equals(order.getStatus())) return false;

        orderRepository.updateOrderStatus(orderId, "CANCELLED");
        return true;
    }

    public SalesReport getSalesReport() {
        String to = LocalDate.now().toString();
        SalesReport report = orderRepository.getSalesSummary(to);
        report.setItems(orderRepository.getSalesItemBreakdown(to));
        return report;
    }

}
