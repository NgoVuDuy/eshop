package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.WishlistRequest;
import com.nvd.electroshop.dto.response.ProductResponse;
import com.nvd.electroshop.dto.response.WishlistResponse;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.Wishlist;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.repository.WishlistRepository;
import com.nvd.electroshop.service.GlobalService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class WishListMapper {

    @Autowired private ProductRepository productRepository;
    @Autowired private GlobalService globalService;
    private ProductMapper productMapper;

    @Lazy
    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    // reponse
    public WishlistResponse mapToWishlistResponse(Wishlist wishlist) {

        return mapToWishlistResponse(wishlist, null);
    }
    public List<WishlistResponse> mapToWishlistResponseList(List<Wishlist> wishlists) {

        return mapToWishlistResponseList(wishlists, null);
    }

    public WishlistResponse mapToWishlistResponse(Wishlist wishlist, List<String> includes) {

        Product product = wishlist.getProduct();
        ProductResponse productResponse = productMapper.mapToProductResponse(product, includes);

        return WishlistResponse.builder()
                .id(wishlist.getId())
                .product(productResponse)
                .build();
    }

    public List<WishlistResponse> mapToWishlistResponseList(List<Wishlist> wishlists, List<String> includes) {

        return wishlists.stream().map(wishlist -> this.mapToWishlistResponse(wishlist, includes)).toList();
    }
    // request
    public Wishlist mapToWishlist(WishlistRequest wishlistRequest) {
        return this.mapToWishlist(wishlistRequest, null);
    }

    public Wishlist mapToWishlist(WishlistRequest wishlistRequest, Wishlist wishlistDetails) {

        Wishlist wishlist = Objects.requireNonNullElseGet(wishlistDetails, Wishlist::new);

        Product product = globalService.getProductById(wishlistRequest.getProductId());

        return Wishlist.builder()
                .product(product)
                .build();
    }
}
