package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.CartItemRequest;
import com.nvd.electroshop.dto.response.CartItemResponse;
import com.nvd.electroshop.dto.response.ProductResponse;
import com.nvd.electroshop.entity.Cart;
import com.nvd.electroshop.entity.CartItem;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class CartItemMapper {

    @Autowired
    private GlobalService globalService;

    private ProductMapper productMapper;

    @Lazy
    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // response
    public CartItemResponse mapToCartItemResponse(CartItem cartItem) {
        return this.mapToCartItemResponse(cartItem, null);
    }

    public List<CartItemResponse> mapToCartItemResponseList(List<CartItem> cartItemList) {

        return this.mapToCartItemResponseList(cartItemList, null);
    }


    public CartItemResponse mapToCartItemResponse(CartItem cartItem, List<String> includes) {

        Product product = cartItem.getProduct();
        Double price = product.getPrice() * cartItem.getQuantity();

        ProductResponse productResponse = productMapper.mapToProductResponse(product, includes);

        return CartItemResponse.builder()
                .id(cartItem.getId())
                .product(productResponse)
                .quantity(cartItem.getQuantity())
                .price(globalService.formatCurrency(price))
                .build();
    }

    public List<CartItemResponse> mapToCartItemResponseList(List<CartItem> cartItemList, List<String> includes) {

        return cartItemList.stream().map(cartItem -> this.mapToCartItemResponse(cartItem, includes)).toList();
    }

    // request
    public CartItem mapToCartItem(CartItemRequest cartItemRequest) {

        return this.mapToCartItem(cartItemRequest, null);
    }

    public List<CartItem> mapToCartItemList(List<CartItemRequest> cartItemRequestList) {

        return this.mapToCartItemList(cartItemRequestList, null);
    }

    public CartItem mapToCartItem(CartItemRequest cartItemRequest, CartItem cartItemDetails) {

        CartItem cartItem = Objects.requireNonNullElseGet(cartItemDetails, CartItem::new);

        Product product = globalService.getProductById(cartItemRequest.getProductId());

        if(cartItem.getQuantity() != null) {

            cartItem.setQuantity(cartItemRequest.getQuantity() + cartItem.getQuantity());

        } else {

            cartItem.setQuantity(cartItemRequest.getQuantity());
        }

        cartItem.setProduct(product);

        return cartItem;
    }

    public List<CartItem> mapToCartItemList(List<CartItemRequest> cartItemRequestList, CartItem cartItemDetails) {

        return cartItemRequestList.stream().map(cartItemRequest -> this.mapToCartItem(cartItemRequest, cartItemDetails)).toList();
    }

    // partial update
    public CartItem mapToCartItemRequireNonNull(CartItemRequest cartItemRequest, CartItem cartItemDetails) {

        CartItem cartItem = Objects.requireNonNullElseGet(cartItemDetails, CartItem::new);

        if(cartItemRequest.getProductId() != null) {

            Product product = globalService.getProductById(cartItemRequest.getProductId());

            cartItem.setProduct(product);
        }

        if (cartItemRequest.getQuantity() != 0) {

            cartItem.setQuantity(cartItemRequest.getQuantity());
        }

        return cartItem;
    }

    public List<CartItem> mapToCartItemListRequireNonNull(List<CartItemRequest> cartItemRequestList, CartItem cartItemDetails) {

        return cartItemRequestList.stream().map(cartItem -> this.mapToCartItemRequireNonNull(cartItem, cartItemDetails)).toList();
    }
}
