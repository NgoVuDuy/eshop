package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.DeleteProductImageRequest;
import com.nvd.electroshop.dto.request.ProductImageRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.ProductImageResponse;
import com.nvd.electroshop.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/uploads")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> uploadProductImages(@ModelAttribute ProductImageRequest productImageRequest) {

        return ResponseEntity.ok(productImageService.uploadProductImage(productImageRequest));
    }

    @DeleteMapping
    public ResponseEntity<Message> deleteProductImage(@RequestBody DeleteProductImageRequest deleteProductImageRequest) {

        return ResponseEntity.ok(productImageService.deleteProductImage(deleteProductImageRequest));
    }
}
