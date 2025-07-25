package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.BrandRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    public ApiResponse<List<BrandResponse>>  getAllBrands();
    public ApiResponse<BrandResponse> getBrandById(Long id);
    public ApiResponse<BrandResponse> createBrand(BrandRequest brandRequest);
    public ApiResponse<BrandResponse> updateBrand(Long id, BrandRequest brandRequest);
    public Message deleteBrand(Long id);
}