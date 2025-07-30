package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.CartItemRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.CartItemResponse;
import com.nvd.electroshop.dto.response.Message;

import java.util.List;

public interface CartItemService {

    ApiResponse<List<CartItemResponse>> getCartItemsForUser();
    ApiResponse<CartItemResponse> createCartItemForUser(CartItemRequest cartItemRequest);
    ApiResponse<CartItemResponse> updateCartItemForUser();
    ApiResponse<Message> deleteCartItemsForUser();
}