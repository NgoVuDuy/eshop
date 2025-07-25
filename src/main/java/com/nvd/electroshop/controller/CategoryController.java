package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.CategoryRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.AttributeResponse;
import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.dto.response.CategoryResponse;
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
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {

        return ResponseEntity.ok(categoryService.getAllCategories()) ;
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getCategoryById(id)) ;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest categoryRequest) {

        return ResponseEntity.ok(categoryService.createCategory(categoryRequest)) ;
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {

        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);
    }

    @GetMapping("{id}/brands")
    public ResponseEntity<ApiResponse<Set<BrandResponse>>> getBrandsByCategoryId(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getBrandsByCategoryId(id));
    }

    @GetMapping("{id}/attributes")
    public ResponseEntity<ApiResponse<Set<AttributeResponse>>> getAttributesByCategoryId(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getAttributesByCategoryId(id));
    }

}
