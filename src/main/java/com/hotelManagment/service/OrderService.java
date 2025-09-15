package com.hotelManagment.service;

import com.hotelManagment.model.Order;
import com.hotelManagment.model.OrderItem;
import com.hotelManagment.model.OrderRequest;
import com.hotelManagment.model.User;
import com.hotelManagment.repository.OrderRepository;
import com.hotelManagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void placeOrder(OrderRequest request) {
        int orderId = orderRepository.insertOrder(request.getCustomerId());

        for (OrderItem item : request.getItems()) {
            orderRepository.insertOrderItem(orderId, item);
        }
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
            return orderRepository.assignChef(orderId, chefId);
        }
        return false;
    }

    public void updateStatus(Integer orderId, String status) {
        orderRepository.updateOrderStatus(orderId, status);
    }

    public List<Order> findByChef(Integer chefId) {
        return orderRepository.findOrdersByChef(chefId);
    }
}
