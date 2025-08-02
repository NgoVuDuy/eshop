package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.OrderRequest;
import com.nvd.electroshop.dto.request.UpdateOrderStatusRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.OrderResponse;
import com.nvd.electroshop.service.OrderService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired private OrderService orderService;

    // users
    @GetMapping("/users/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrderForUser() {

        return ResponseEntity.ok(orderService.getAllUserOrders());
    }

    @GetMapping("/users/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getUserOrderById(@PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.getUserOrderById(orderId));
    }

    @PostMapping("/users/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequest orderRequest) {

        return ResponseEntity.ok(orderService.createUserOrder(orderRequest));
    }

    @DeleteMapping("/users/orders/{orderId}")
    public ResponseEntity<Message> deleteUserOrder(@PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.deleteUserOrder(orderId));
    }

    // admin
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {

        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, updateOrderStatusRequest));
    }
}