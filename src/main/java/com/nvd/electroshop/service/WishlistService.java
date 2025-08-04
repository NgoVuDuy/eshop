package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.WishlistRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.WishlistResponse;

import java.util.List;

public interface WishlistService {

    public ApiResponse<List<WishlistResponse>> getAllUserWishlists(List<String> includes);
    public ApiResponse<WishlistResponse> createUserWishlist(WishlistRequest wishlistRequest);
    public Message deleteUserWishlist(Long id);
}