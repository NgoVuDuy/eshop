package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.WishlistRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.WishlistResponse;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.entity.Wishlist;
import com.nvd.electroshop.exception.ConflictException;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.WishListMapper;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.repository.WishlistRepository;
import com.nvd.electroshop.service.GlobalService;
import com.nvd.electroshop.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WishListMapper wishListMapper;

    @Override
    public ApiResponse<List<WishlistResponse>> getAllUserWishlists(List<String> includes) {

        User user = globalService.getUserByToken();

        List<Wishlist> wishlists = user.getWishlists();
        List<WishlistResponse> wishlistResponses = wishListMapper.mapToWishlistResponseList(wishlists, includes);

        return new ApiResponse<>(1, wishlistResponses);
    }

    @Override
    public ApiResponse<WishlistResponse> createUserWishlist(WishlistRequest wishlistRequest) {

        Wishlist wishlist = wishListMapper.mapToWishlist(wishlistRequest);
        User user = globalService.getUserByToken();

        boolean existsUserWishlist = isExistsUserWishlist(user, wishlist);

        if(existsUserWishlist) {
            throw new ConflictException("Đã tồn tại sản phẩm yêu thích");
        }

        wishlist.setUser(user);
        wishlist = wishlistRepository.save(wishlist);

        WishlistResponse wishlistResponse =  wishListMapper.mapToWishlistResponse(wishlist);

        return new ApiResponse<>(1, wishlistResponse);
    }

    @Override
    public Message deleteUserWishlist(Long id) {

        Wishlist wishlist = getWishlist(id);
        User user = globalService.getUserByToken();

        boolean existsUserWishlist = isExistsUserWishlist(user, wishlist);
        if (!existsUserWishlist) {

            throw new ResourceNotFoundException("Không tồn tại danh mục yêu thích");
        }

        wishlistRepository.delete(wishlist);

        return new Message(1, "Xóa mục yêu thích thành công") ;
    }

    public Wishlist getWishlist(Long id) {

        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(id);

        if(wishlistOptional.isEmpty()) {

            throw new ResourceNotFoundException("Không tìm thấy mục yêu thích");
        }

        return wishlistOptional.get();
    }

    public boolean isExistsUserWishlist(User user, Wishlist wishlist) {

        return wishlistRepository.existsByUserAndProduct(user, wishlist.getProduct());
    }

}
