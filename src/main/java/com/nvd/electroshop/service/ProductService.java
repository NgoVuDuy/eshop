package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.ProductRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.Review;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ApiResponse<List<ProductResponse>> getAllProducts(List<String> includes);
    ApiResponse<ProductResponse> createProduct(ProductRequest productRequest);
    ApiResponse<ProductResponse> getProductById(Long id, List<String> includes);
    ApiResponse<ProductResponse> updateProduct(Long id, ProductRequest productRequest);
    ApiResponse<ProductResponse> partialUpdateProduct(Long id, Map<String, Object> requests);
    Message deleteProduct(Long id);
    ApiResponse<List<ReviewResponse>> getReviewsByProductId(Long id);
    ApiResponse<List<AttributeProductResponse>> getAttributeProductsById(Long id);
    ApiResponse<List<CategoryResponse>> getCategoriesByProductId(Long id);
    ApiResponse<BrandResponse> getBrandByProductId(Long id);
}