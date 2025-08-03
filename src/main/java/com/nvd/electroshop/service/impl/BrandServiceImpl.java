package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.BrandRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.BrandMapper;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.repository.CategoryRepository;
import com.nvd.electroshop.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandServiceImpl(
            BrandRepository brandRepository,
            CategoryRepository categoryRepository,
            BrandMapper brandMapper
    ) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public ApiResponse<List<BrandResponse>> getAllBrands(List<String> includes) {

        List<Brand> brandList = brandRepository.findAll();
        List<BrandResponse> brandResponseList = brandMapper.mapToBrandResponseList(brandList, includes);

        return new ApiResponse<>(1, brandResponseList);
    }

    @Override
    public ApiResponse<BrandResponse> getBrandById(Long id, List<String> includes) {

        Brand brand = getBrand(id);
        BrandResponse brandResponse = brandMapper.mapToBrandResponse(brand, includes);

        return new ApiResponse<>(1, brandResponse);
    }

    @Override
    public ApiResponse<BrandResponse> createBrand(BrandRequest brandRequest) {

        Brand brand = brandMapper.mapToBrand(brandRequest);
        brand = brandRepository.save(brand);

        BrandResponse brandResponse = brandMapper.mapToBrandResponse(brand);

        return new ApiResponse<>(1, brandResponse);
    }

    @Override
    public ApiResponse<BrandResponse> updateBrand(Long id, BrandRequest brandRequest) {

        Brand brand = getBrand(id);
        brand = brandMapper.mapToBrand(brandRequest, brand);
        brand = brandRepository.save(brand);

        BrandResponse brandResponse = brandMapper.mapToBrandResponse(brand);

        return new ApiResponse<>(1, brandResponse);
    }

    @Override
    public Message deleteBrand(Long id) {

        Brand brand = getBrand(id);
        brandRepository.delete(brand);

        return new Message(1, "Xóa hãng thành công");
    }

    private Brand getBrand(Long id) {

        Optional<Brand> brandOptional = brandRepository.findById(id);

        if(brandOptional.isEmpty()) {
            throw new ResourceNotFoundException("Không tìm thấy hãng");
        }

        return brandOptional.get();
    }
}