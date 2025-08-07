package com.nvd.electroshop.service;


import com.cloudinary.Api;
import com.nvd.electroshop.dto.request.AuthRequest;
import com.nvd.electroshop.dto.request.LogoutRequest;
import com.nvd.electroshop.dto.request.RefreshTokenRequest;
import com.nvd.electroshop.dto.request.VerifyRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.entity.User;

public interface AuthService {

    public Message register(AuthRequest authRequest);
    public ApiResponse<AuthResponse> login(AuthRequest authRequest);
    public Message logout(LogoutRequest logoutRequest);
    public ApiResponse<RefreshTokenResponse> refresh(RefreshTokenRequest refreshTokenRequest);

    public ApiResponse<VerifyResponse> verifyToken(VerifyRequest verifyRequest);
}
