package com.nvd.electroshop.service;


import com.nvd.electroshop.dto.request.AuthRequest;
import com.nvd.electroshop.dto.request.VerifyRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.AuthResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.User;

public interface AuthService {

    public Message register(AuthRequest authRequest);
    public ApiResponse<AuthResponse> login(AuthRequest authRequest);

    public Message verifyToken(VerifyRequest verifyRequest);
}
