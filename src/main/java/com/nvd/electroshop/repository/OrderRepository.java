package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.Order;
import com.nvd.electroshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public boolean existsByUser(User user);
    public List<Order> findByUser(User user);

    public Optional<Order> findByIdAndUser(Long id, User user);
}