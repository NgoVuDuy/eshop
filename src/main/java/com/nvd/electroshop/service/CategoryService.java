package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.CategoryRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    public ApiResponse<List<CategoryResponse>>  getAllCategories(List<String> includes);
    public ApiResponse<CategoryResponse> getCategoryById(Long id, List<String> includes);
    public ApiResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest);
    public ApiResponse<CategoryResponse> updateCategory(Long id, CategoryRequest categoryRequest);
    public Message deleteCategory(Long id);

    public ApiResponse<List<BrandResponse>> getBrandsByCategoryId(Long id);
    public ApiResponse<List<AttributeResponse>> getAttributesByCategoryId(Long id);
    public ApiResponse<List<ProductResponse>> getProductsByCategoryId(Long id);
}