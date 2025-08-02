package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.CategoryRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories(@RequestParam(value = "include", required = false) List<String> includes) {

        return ResponseEntity.ok(categoryService.getAllCategories(includes)) ;
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id, @RequestParam(value = "include", required = false) List<String> includes) {

        return ResponseEntity.ok(categoryService.getCategoryById(id, includes)) ;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest categoryRequest) {

        return ResponseEntity.ok(categoryService.createCategory(categoryRequest)) ;
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {

        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequest));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Message> deleteCategory(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @GetMapping("{id}/brands")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getBrandsByCategoryId(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getBrandsByCategoryId(id));
    }

    @GetMapping("{id}/attributes")
    public ResponseEntity<ApiResponse<List<AttributeResponse>>> getAttributesByCategoryId(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getAttributesByCategoryId(id));
    }

}
