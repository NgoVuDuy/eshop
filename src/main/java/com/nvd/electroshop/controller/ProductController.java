package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.ProductRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.ProductResponse;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>>  getAllProducts() {

        return ResponseEntity.ok(productService.getAllProducts());
    }

//    @GetMapping("{id}")
//    public Product getProductById(@PathVariable Long id) {
//
//        return productService.getProductById(id);
//    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest productRequest) {

        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

//    @PutMapping("{id}")
//    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
//
//        return productService.updateProduct(id, product);
//    }
//
//    @DeleteMapping("{id}")
//    public void deleteProduct(@PathVariable Long id) {
//
//        productService.deleteProduct(id);
//    }
}
