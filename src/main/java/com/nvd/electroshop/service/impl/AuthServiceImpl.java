package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.AuthRequest;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.repository.AuthRepository;
import com.nvd.electroshop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Message register(AuthRequest authRequest) {

        Optional<User> userOptional = authRepository.findByUsername(authRequest.getUsername());

        if (userOptional.isPresent()) {
            throw new RuntimeException("Tên tài khoản đã tồn tại");
        }

        String passwordEd = passwordEncoder.encode(authRequest.getPassword());

        User user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEd)
                .build();

        authRepository.save(user);

        return new Message(1, "Tạo tài khoản thành công");
    }

    @Override
    public User login(AuthRequest authRequest) {

        Optional<User> userOptional = authRepository.findByUsername(authRequest.getUsername());

        if(userOptional.isEmpty()) {
            throw new RuntimeException("Tên tài khoản hoặc mật khẩu không đúng");
        }

        User user = userOptional.get();

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {

            throw new RuntimeException("Tên tài khoản hoặc mật khẩu không đúng");
        }

        return user;
    }
}
