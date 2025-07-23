package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.entity.Wishlist;
import org.springframework.data.repository.CrudRepository;

public interface WishlistRepository extends CrudRepository<Wishlist, Long> {

    public boolean existsByUserAndProduct(User user, Product product);
}
