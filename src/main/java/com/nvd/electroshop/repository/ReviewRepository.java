package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.Review;
import com.nvd.electroshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    public List<Review> findByProduct_Id(Long id);
    public boolean existsByUserAndProduct(User user, Product product);
    public Review findByUserAndProduct(User user, Product product);
}
