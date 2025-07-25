package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.repository.CartRepository;
import com.nvd.electroshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/users/carts")
    public ResponseEntity<Message> createCart() {

        return ResponseEntity.ok(cartService.createCart());
    }

    @DeleteMapping("/users/carts")
    public ResponseEntity<Message> deleteCart() {

        return ResponseEntity.ok(cartService.deleteCart());
    }

}
