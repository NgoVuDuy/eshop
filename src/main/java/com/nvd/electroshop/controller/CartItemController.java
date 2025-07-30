package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.CartItemRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.CartItemResponse;
import com.nvd.electroshop.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> getCartItemsForUser() {

        return ResponseEntity.ok(cartItemService.getCartItemsForUser());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartItemResponse>> createCartItemForUser(@RequestBody CartItemRequest cartItemRequest) {

        return ResponseEntity.ok(cartItemService.createCartItemForUser(cartItemRequest));
    }
}
