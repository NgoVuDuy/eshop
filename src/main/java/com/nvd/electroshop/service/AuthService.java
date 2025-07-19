package com.nvd.electroshop.service;


import com.nvd.electroshop.dto.request.AuthRequest;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.User;

public interface AuthService {

    public Message register(AuthRequest authRequest);
    public User login(AuthRequest authRequest);
}
