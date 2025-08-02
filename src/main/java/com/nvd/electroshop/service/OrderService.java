package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.OrderRequest;
import com.nvd.electroshop.dto.request.UpdateOrderStatusRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    //user
    ApiResponse<List<OrderResponse>> getAllUserOrders();
    ApiResponse<OrderResponse> getUserOrderById(Long id);
    ApiResponse<OrderResponse> createUserOrder(OrderRequest orderRequest);
    ApiResponse<OrderResponse> updateUserOrder(Long id, OrderRequest orderRequest);
    Message deleteUserOrder(Long id);
    //admin
    ApiResponse<List<OrderResponse>> getAllOrders();
    ApiResponse<OrderResponse> updateOrderStatus(Long id, UpdateOrderStatusRequest updateOrderStatusRequest);
}