package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.ProductRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.ProductResponse;
import com.nvd.electroshop.entity.Product;

import java.util.List;

public interface ProductService {

    public ApiResponse<List<ProductResponse> > getAllProducts();
    public ApiResponse<ProductResponse> createProduct(ProductRequest productRequest);
    public ApiResponse<ProductResponse> getProductById(Long id);
    public ApiResponse<ProductResponse> updateProduct(Long id, ProductRequest productRequest);
    public Message deleteProduct(Long id);
}