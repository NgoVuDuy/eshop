package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.CartItemRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.CartItemResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/cart/items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> getAllUserCartItems(@RequestParam(value = "include", required = false) List<String> includes) {

        return ResponseEntity.ok(cartItemService.getAllUserCartItems(includes));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CartItemResponse>> getUserCartItemById(@PathVariable Long id, @RequestParam(value = "include", required = false) List<String> includes) {

        return ResponseEntity.ok(cartItemService.getUserCartItemById(id, includes));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartItemResponse>> createUserCartItem(@RequestBody CartItemRequest cartItemRequest) {

        return ResponseEntity.ok(cartItemService.createUserCartItem(cartItemRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<CartItemResponse>> updateUserCartItem (@RequestBody CartItemRequest cartItemRequest, @PathVariable Long id) {

        return ResponseEntity.ok(cartItemService.updateUserCartItem(id, cartItemRequest));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ApiResponse<CartItemResponse>> partialUpdateUserCartItem (@RequestBody CartItemRequest cartItemRequest, @PathVariable Long id) {

        return ResponseEntity.ok(cartItemService.partialUserCartItem(id, cartItemRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteUserCartItem (@RequestBody CartItemRequest cartItemRequest, @PathVariable Long id) {

        return ResponseEntity.ok(cartItemService.deleteUserCartItem(id));
    }

    @DeleteMapping
    public ResponseEntity<Message> deleteAllUserCartItem() {

        return ResponseEntity.ok(cartItemService.deleteAllUserCartItem());
    }
}