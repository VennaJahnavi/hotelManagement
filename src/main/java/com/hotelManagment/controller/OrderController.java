package com.hotelManagment.controller;

import com.hotelManagment.model.Order;
import com.hotelManagment.model.OrderRequest;
import com.hotelManagment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return ResponseEntity.ok("Order placed successfully");
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable int customerId) {
        List<Order> orders = orderService.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/assign-chef/{chefId}")
    public ResponseEntity<String> assignChef(@PathVariable Integer orderId,
                                             @PathVariable Integer chefId,
                                             @RequestParam Integer managerId) {
        boolean assigned = orderService.assignChef(orderId, chefId, managerId);
        if (assigned) return ResponseEntity.ok("Chef assigned");
        return ResponseEntity.status(403).body("Unauthorized or invalid manager");
    }

    // Update order status
    @PutMapping("/{orderId}/status")
    public void updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        orderService.updateStatus(orderId, status);
    }

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<Order>> getOrdersByChef(@PathVariable Integer chefId) {
        List<Order> orders = orderService.findByChef(chefId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer orderId) {

        boolean cancelled = orderService.cancelOrder(orderId);

        if (!cancelled) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Order cannot be cancelled or not found."));
        }

        return ResponseEntity.ok(Map.of("message", "Order successfully cancelled."));
    }

    @GetMapping("/report/sales")
    public ResponseEntity<?> getSalesReport() {
        return ResponseEntity.ok(orderService.getSalesReport());
    }
}
