package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.WishlistRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.WishlistResponse;
import com.nvd.electroshop.entity.Wishlist;

import java.util.List;

public interface WishlistService {

    public ApiResponse<List<WishlistResponse>> getWishlistsByUsername();
    public ApiResponse<WishlistResponse> createWishlist(WishlistRequest wishlistRequest);
    public Message deleteWishlist(Long id);
}