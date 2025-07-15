package com.nvd.electroshop.service;

import com.nvd.electroshop.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public Iterable<Category> getAllCategories();
    public Category getCategoryById(Long id);
    public Category createCategory(Category category);
    public Category updateCategory(Long id, Category category);
    public void deleteCategory(Long id);
}
