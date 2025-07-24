package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.AuthRequest;
import com.nvd.electroshop.dto.request.VerifyRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.AuthResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("register")
    public ResponseEntity<Message> register(@RequestBody AuthRequest authRequest) {

        return ResponseEntity.ok(authService.register(authRequest));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AuthResponse>> login (@RequestBody AuthRequest authRequest) {

        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PostMapping("token/verify")
    public ResponseEntity<Message> verifyToken(@RequestBody VerifyRequest verifyRequest) {

        return ResponseEntity.ok(authService.verifyToken(verifyRequest));
    }
}
