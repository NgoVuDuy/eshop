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
@RequestMapping("/users/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getAllUserWishlists(@RequestParam(value = "include", required = false) List<String> includes) {

        return ResponseEntity.ok(wishlistService.getAllUserWishlists(includes));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WishlistResponse>> createUserWishlist(@RequestBody WishlistRequest wishlistRequest) {

        return ResponseEntity.ok(wishlistService.createUserWishlist(wishlistRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteUserWishlist(@PathVariable Long id) {

        return ResponseEntity.ok(wishlistService.deleteUserWishlist(id));
    }
}