package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.repository.UserRepository;
import com.nvd.electroshop.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GlobalServiceImpl implements GlobalService {

    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;

    @Override
    public User getUserByToken() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isEmpty()) {

            throw new RuntimeException("Không tìm thấy người dùng");
        }

        return userOptional.get();
    }

    @Override
    public Product getProductById(Long productId) {

        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        return productOptional.get();
    }
}
