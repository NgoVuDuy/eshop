package com.nvd.electroshop.service;


import com.nvd.electroshop.entity.Cart;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.repository.CartRepository;

public interface GlobalService {

    public User getUserByToken();
    public Product getProductById(Long productId);

    public String formatCurrency(double amount);
}
