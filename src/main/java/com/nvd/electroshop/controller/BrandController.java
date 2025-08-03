package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.BrandRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands(@RequestParam(value = "include", required = false) List<String> includes) {

        return ResponseEntity.ok( brandService.getAllBrands(includes)) ;
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById(@PathVariable Long id, @RequestParam(value = "include", required = false) List<String> includes) {
        return ResponseEntity.ok(brandService.getBrandById(id, includes)) ;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(@RequestBody BrandRequest brandRequest) {

        return ResponseEntity.ok(brandService.createBrand(brandRequest));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(@PathVariable Long id, @RequestBody BrandRequest brandRequest) {

        return ResponseEntity.ok(brandService.updateBrand(id, brandRequest)) ;
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Message> deleteBrand(@PathVariable Long id) {

        return ResponseEntity.ok(brandService.deleteBrand(id));
    }
}
