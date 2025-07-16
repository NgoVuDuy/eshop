package com.nvd.electroshop.service;

import com.nvd.electroshop.entity.Product;

public interface ProductService {

    public Iterable<Product> getAllProducts();
    public Product createProduct(Product product);
    public Product getProductById(Long id);
    public Product updateProduct(Long id, Product product);
    public void deleteProduct(Long id);
}
