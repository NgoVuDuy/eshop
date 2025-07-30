package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.CartItemRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.CartItemResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Cart;
import com.nvd.electroshop.entity.CartItem;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.repository.CartItemRepository;
import com.nvd.electroshop.repository.CartRepository;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.service.CartItemService;
import com.nvd.electroshop.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {


    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GlobalService globalService;

    @Override
    public ApiResponse<List<CartItemResponse>> getCartItemsForUser() {

        User user = globalService.getUserByToken();

        if (!cartRepository.existsByUser_Id(user.getId())) {
            throw new RuntimeException("Bạn chưa có giỏ hàng");
        }

        Cart cart = cartRepository.findByUser_Id(user.getId());

        List<CartItem> cartItemList = cart.getCartItems();

        List<CartItemResponse> cartItemResponseList = new ArrayList<>();

        for (CartItem cartItem : cartItemList) {

            Optional<Product> productOptional = productRepository.findById(cartItem.getProduct().getId());

            if (productOptional.isEmpty()) {
                throw new RuntimeException("Không tìm thấy sản phẩm");
            }

            Product product = productOptional.get();

            CartItemResponse cartItemResponse = CartItemResponse.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .stockQuantity(product.getStockQuantity())
                    .quantity(cartItem.getQuantity())
                    .build();

            cartItemResponseList.add(cartItemResponse);
        }


        return new ApiResponse<>(1, cartItemResponseList);
    }

    @Override
    public ApiResponse<CartItemResponse> createCartItemForUser(CartItemRequest cartItemRequest) {

        User user = globalService.getUserByToken();

        if (!cartRepository.existsByUser_Id(user.getId())) {
            throw new RuntimeException("Bạn chưa có giỏ hàng");
        }

        Cart cart = cartRepository.findByUser_Id(user.getId());

        Optional<Product> productOptional = productRepository.findById(cartItemRequest.getProductId());

        if (productOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        Product product = productOptional.get();

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(cartItemRequest.getQuantity())
                .build();

        cartItemRepository.save(cartItem);

        CartItemResponse cartItemResponse = CartItemResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .quantity(cartItem.getQuantity())
                .build();

        return new ApiResponse<>(1, cartItemResponse);
    }

    @Override
    public ApiResponse<CartItemResponse> updateCartItemForUser() {

        return null;
    }

    @Override
    public ApiResponse<Message> deleteCartItemsForUser() {
        return null;
    }
}
