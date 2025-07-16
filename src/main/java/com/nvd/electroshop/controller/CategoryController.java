package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.CategoryRequest;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Iterable<Category> getAllCategories() {

        return categoryService.getAllCategories();
    }

    @GetMapping("{id}")
    public Category getCategoryById(@PathVariable Long id) {

        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public Category createCategory(@RequestBody CategoryRequest categoryRequest) {

        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping("{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {

        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);
    }

}
