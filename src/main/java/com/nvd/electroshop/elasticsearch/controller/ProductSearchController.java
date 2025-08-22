package com.nvd.electroshop.elasticsearch.controller;

import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.elasticsearch.model.ProductSearch;
import com.nvd.electroshop.elasticsearch.service.ProductSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping("products")
    public ResponseEntity<ApiResponse<List<ProductSearch>>> searchProducts(@RequestParam String keyword) {

        return ResponseEntity.ok(new ApiResponse<>(1, productSearchService.searchProducts(keyword))) ;
    }
}
