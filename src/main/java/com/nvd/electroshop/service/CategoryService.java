package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.CategoryRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.CategoryResponse;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryService {

    public ApiResponse<List<CategoryResponse>>  getAllCategories();
    public ApiResponse<CategoryResponse> getCategoryById(Long id);
    public ApiResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest);
    public Category updateCategory(Long id, Category category);
    public void deleteCategory(Long id);

    // Lấy các hãng của danh mục
    public ApiResponse<Set<Brand>> getBrandsByCategoryId(Long id);

    // Lấy các thuộc tính của danh mục
    public ApiResponse<Set<Attribute>> getAttributesByCategoryId(Long id);
}