package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Cart;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.repository.CartRepository;
import com.nvd.electroshop.repository.UserRepository;
import com.nvd.electroshop.service.CartService;
import com.nvd.electroshop.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GlobalService globalService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Message createCart() {

        User user = globalService.getUserByToken();

        if(cartRepository.existsByUser_Id(user.getId())) {

            throw new RuntimeException("Giỏ hàng đã tồn tại");
        }

        Cart cart = new Cart();
        cart.setUser(user);

        cartRepository.save(cart);

        return new Message(1, "Tạo giỏ hàng thành công");
    }

    @Override
    public Message deleteCart() {

        User user = globalService.getUserByToken();

        if(!cartRepository.existsByUser_Id(user.getId())) {

            throw new RuntimeException("Không tìm thấy giỏ hàng");
        }

        Cart cart = cartRepository.findByUser_Id(user.getId());

        System.out.println(cart.getUser().getUsername());

        user.setCart(null);
        userRepository.save(user);

        cartRepository.delete(cart);

        return new Message(1, "Xóa giỏ hàng thành công");
    }
}
