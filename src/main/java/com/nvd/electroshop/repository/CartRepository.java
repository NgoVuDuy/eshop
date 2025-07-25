package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    public boolean existsByUser_Id(Long id);
    public Cart findByUser_Id(Long id);
}
