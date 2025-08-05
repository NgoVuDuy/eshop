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
import org.springframework.transaction.annotation.Transactional;

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

        if(!cartRepository.existsByUser_Id(user.getId())) {
            throw new ResourceNotFoundException("Bạn chưa có giỏ hàng");
        }

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
        Product product = globalService.getProductById(cartItemRequest.getProductId());// Lấy sản phẩm cần thêm

        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartAndProduct(cart, product);

        CartItem cartItem;
        // Kiểm tra sản phẩm này có tồn tại trong giỏ hàng hay chưa
        if(cartItemOptional.isEmpty()) { // Không - tạo mới

            cartItem = cartItemMapper.mapToCartItem(cartItemRequest); // Mapper


        } else {

            cartItem = cartItemOptional.get(); //Tồn tại - Lấy sản phẩm
            cartItem = cartItemMapper.mapToCartItem(cartItemRequest, cartItem); // Cập nhật số lượng
        }

        cartItem.setCart(cart); // set giỏ hàng cho item

        cartItem = cartItemRepository.save(cartItem); // Lưu item
        CartItemResponse cartItemResponse = cartItemMapper.mapToCartItemResponse(cartItem); // Mapper

        return new ApiResponse<>(1, cartItemResponse);

        // return

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

    @Override
    @Transactional
    public Message deleteAllUserCartItem() {

        User user = globalService.getUserByToken();
        Cart cart = user.getCart();

        cart.getCartItems().clear();
//        cartRepository.save(cart);
//        cartItemRepository.deleteAll(cartItemList);

        return new Message(1, "Xóa chi tiết giỏ hàng thành công");
    }

    private CartItem getCartItem(Long id) {

        // Kiểm tra cartitem có phải thuộc người dùng hiện tại không
        User user = globalService.getUserByToken();
        Cart cart = user.getCart();

        Optional<CartItem> cartItemOptional = cartItemRepository.findByIdAndCart(id, cart);

        if(cartItemOptional.isEmpty()) {
            throw new ResourceNotFoundException("Không tìm thấy chi tiết giỏ hàng");
        }

        return cartItemOptional.get();
    }
}
