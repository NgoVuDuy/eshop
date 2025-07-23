package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.WishlistRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.WishlistResponse;
import com.nvd.electroshop.entity.Wishlist;
import com.nvd.electroshop.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getWishlistsByUsername() {

        return ResponseEntity.ok(wishlistService.getWishlistsByUsername());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WishlistResponse>> createWishlist(@RequestBody WishlistRequest wishlistRequest) {

        return ResponseEntity.ok(wishlistService.createWishlist(wishlistRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteWishlist(@PathVariable Long id) {

        return ResponseEntity.ok(wishlistService.deleteWishlist(id));
    }
}