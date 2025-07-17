package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()) {

            return product.get();
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {

        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()) {

            Product product = productOptional.get();

            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());

            return productRepository.save(product);
        } else {
            throw new RuntimeException("Cập nhật sản phẩm thất bại");
        }
    }

    @Override
    public void deleteProduct(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()) {

            productRepository.delete(productOptional.get());
        } else {

            throw new RuntimeException("Xóa sản phẩm thất bại");
        }
    }
}
