package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.Cart;
import com.nvd.electroshop.entity.CartItem;
import com.nvd.electroshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    public List<CartItem> findByCart_Id(Long id);

    public Optional<CartItem> findByIdAndCart(Long id, Cart cart);
    public Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}