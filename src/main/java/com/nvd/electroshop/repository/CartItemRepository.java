package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    public List<CartItem> findByCart_Id(Long id);
}