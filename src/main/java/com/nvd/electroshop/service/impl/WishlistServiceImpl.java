package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.WishlistRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.WishlistResponse;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.entity.Wishlist;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.repository.UserRepository;
import com.nvd.electroshop.repository.WishlistRepository;
import com.nvd.electroshop.service.GlobalService;
import com.nvd.electroshop.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private GlobalService globalService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public ApiResponse<List<WishlistResponse>> getWishlistsByUsername() {

        User user = globalService.getUserByToken();

        List<Wishlist> userWishlists = user.getWishlists();

        List<WishlistResponse> wishlistResponses = userWishlists.stream().map
                (userWishlist ->
                        WishlistResponse.builder()
                            .id(userWishlist.getProduct().getId())
                            .price(userWishlist.getProduct().getPrice())
                            .name(userWishlist.getProduct().getName())
                            .stockQuantity(userWishlist.getProduct().getStockQuantity())
                            .build()
                ).toList();

        return new ApiResponse<>(1, wishlistResponses);
    }

    @Override
    public ApiResponse<WishlistResponse> createWishlist(WishlistRequest wishlistRequest) {

        Optional<Product> productOptional = productRepository.findById(wishlistRequest.getProductId());

        if(productOptional.isEmpty()) {

            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
        Product product = productOptional.get();
        User user = globalService.getUserByToken();


        boolean existsWishlist = wishlistRepository.existsByUserAndProduct(user, product);

        if(existsWishlist) {
            throw new RuntimeException("Đã tồn tại sản phẩm yêu thích");
        }

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .build();

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        WishlistResponse wishlistResponse =  WishlistResponse.builder()
                .id(savedWishlist.getProduct().getId())
                .price(savedWishlist.getProduct().getPrice())
                .name(savedWishlist.getProduct().getName())
                .stockQuantity(savedWishlist.getProduct().getStockQuantity())
                .build();

        return new ApiResponse<>(1, wishlistResponse);
    }

    @Override
    public Message deleteWishlist(Long id) {

        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(id);

        if(wishlistOptional.isEmpty()) {

            throw new RuntimeException("Không tìm thấy mục yêu thích");
        }

        Wishlist wishlist = wishlistOptional.get();

        wishlistRepository.delete(wishlist);

        return new Message(1, "Xóa mục yêu thích thành công") ;
    }
}
