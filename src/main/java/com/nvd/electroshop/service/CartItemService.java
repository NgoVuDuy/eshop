package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.CartItemRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.CartItemResponse;
import com.nvd.electroshop.dto.response.Message;

import java.util.List;

public interface CartItemService {

    ApiResponse<List<CartItemResponse>> getAllUserCartItems(List<String> includes);
    ApiResponse<CartItemResponse> getUserCartItemById(Long id, List<String> includes);
    ApiResponse<CartItemResponse> createUserCartItem(CartItemRequest cartItemRequest);
    ApiResponse<CartItemResponse> updateUserCartItem(Long id, CartItemRequest cartItemRequest);
    ApiResponse<CartItemResponse> partialUserCartItem(Long id, CartItemRequest cartItemRequest);

    Message deleteAllUserCartItem();
    Message deleteUserCartItem(Long id);
}