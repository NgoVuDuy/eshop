package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.DeleteProductImageRequest;
import com.nvd.electroshop.dto.request.ProductImageRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.ProductImageResponse;
import com.nvd.electroshop.entity.ProductImage;
import com.nvd.electroshop.repository.ProductImageRepository;

import java.util.List;

public interface ProductImageService {

    public ApiResponse<List<ProductImageResponse>> uploadProductImage(ProductImageRequest productImageRequest);
    public Message deleteProductImage(DeleteProductImageRequest deleteProductImageRequest);
}
