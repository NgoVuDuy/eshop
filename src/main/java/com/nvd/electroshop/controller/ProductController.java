package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.ProductRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>>  getAllProducts(@RequestParam(value = "include", required = false) List<String> includes) {

        return ResponseEntity.ok(productService.getAllProducts(includes));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id, @RequestParam(value = "include", required = false) List<String> includes) {

        return ResponseEntity.ok(productService.getProductById(id, includes));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest productRequest) {

        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {

        return ResponseEntity.ok(productService.updateProduct(id, productRequest)) ;
    }

    @GetMapping("{productId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByProductId(@PathVariable Long productId) {

        return ResponseEntity.ok(productService.getReviewsByProductId(productId));
    }

    @GetMapping("{productId}/brand")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandByProductId(@PathVariable Long productId) {

        return ResponseEntity.ok(productService.getBrandByProductId(productId));
    }
    @GetMapping("{productId}/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoriesByProductId(@PathVariable Long productId) {

        return ResponseEntity.ok(productService.getCategoriesByProductId(productId));
    }
    @GetMapping("{productId}/attributes")
    public ResponseEntity<ApiResponse<List<AttributeProductResponse>>> getAttributeProductsById(@PathVariable Long productId) {

        return ResponseEntity.ok(productService.getAttributeProductsById(productId));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Message> deleteProduct(@PathVariable Long id) {

        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
