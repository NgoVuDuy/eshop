package com.nvd.electroshop.controller;

import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public Iterable<Product> getAllProducts() {

        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable Long id) {

        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {

        return productService.createProduct(product);
    }

    @PutMapping("{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {

        return productService.updateProduct(id, product);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
    }
}
