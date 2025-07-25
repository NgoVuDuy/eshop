package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;

public interface CartService {

    public Message createCart();

    public Message deleteCart();
}
