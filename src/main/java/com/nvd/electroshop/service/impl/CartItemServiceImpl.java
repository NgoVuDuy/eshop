package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.CartItemRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.CartItemResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Cart;
import com.nvd.electroshop.entity.CartItem;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.CartItemMapper;
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
import java.util.OptionalInt;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final GlobalService globalService;

    private final CartItemMapper cartItemMapper;

    public CartItemServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, GlobalService globalService, CartItemMapper cartItemMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.globalService = globalService;
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    public ApiResponse<List<CartItemResponse>> getAllUserCartItems(List<String> includes) {

        User user = globalService.getUserByToken();
        Cart cart = user.getCart();

        List<CartItem> cartItemList = cart.getCartItems();
        List<CartItemResponse> cartItemResponseList = cartItemMapper.mapToCartItemResponseList(cartItemList, includes);

        return new ApiResponse<>(1, cartItemResponseList);
    }

    @Override
    public ApiResponse<CartItemResponse> getUserCartItemById(Long id, List<String> includes) {

        CartItem cartItem = getCartItem(id);
        CartItemResponse cartItemResponse = cartItemMapper.mapToCartItemResponse(cartItem, includes);

        return new ApiResponse<>(1, cartItemResponse);
    }

    @Override
    public ApiResponse<CartItemResponse> createUserCartItem(CartItemRequest cartItemRequest) {

        User user = globalService.getUserByToken(); // Lấy người dùng
        Cart cart = user.getCart(); // Lấy giỏ hàng

        CartItem cartItem = cartItemMapper.mapToCartItem(cartItemRequest); // Mapper
        cartItem.setCart(cart); // set giỏ hàng cho item

        cartItem = cartItemRepository.save(cartItem); // Lưu item
        CartItemResponse cartItemResponse = cartItemMapper.mapToCartItemResponse(cartItem); // Mapper

        return new ApiResponse<>(1, cartItemResponse);
    }

    @Override
    public ApiResponse<CartItemResponse> updateUserCartItem(Long id, CartItemRequest cartItemRequest) {

        CartItem cartItem = getCartItem(id);
        cartItem = cartItemMapper.mapToCartItem(cartItemRequest, cartItem);

        cartItem = cartItemRepository.save(cartItem);
        CartItemResponse cartItemResponse = cartItemMapper.mapToCartItemResponse(cartItem);

        return new ApiResponse<>(1, cartItemResponse);
    }

    @Override
    public ApiResponse<CartItemResponse> partialUserCartItem(Long id, CartItemRequest cartItemRequest) {

        CartItem cartItem = getCartItem(id);

        cartItem = cartItemMapper.mapToCartItemRequireNonNull(cartItemRequest, cartItem);
        cartItem = cartItemRepository.save(cartItem);
        CartItemResponse cartItemResponse = cartItemMapper.mapToCartItemResponse(cartItem);

        return new ApiResponse<>(1, cartItemResponse);
    }

    @Override
    public Message deleteUserCartItem(Long id) {

        cartItemRepository.deleteById(id);
        return new Message(1, "Xóa chi tiết giỏ hàng thành công");
    }

    private CartItem getCartItem(Long id) {

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);

        if(cartItemOptional.isEmpty()) {
            throw new ResourceNotFoundException("Không tìm thấy chi tiết giỏ hàng");
        }

        return cartItemOptional.get();
    }
}
