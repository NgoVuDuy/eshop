package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.BrandRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.repository.CategoryRepository;
import com.nvd.electroshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ApiResponse<List<BrandResponse>> getAllBrands() {

        List<Brand> brandList = brandRepository.findAll();

        List<BrandResponse> brandResponseList = brandList.stream().map(brand ->

                BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build()).toList();

        return new ApiResponse<>(1, brandResponseList);
    }

    @Override
    public ApiResponse<BrandResponse> getBrandById(Long id) {

        Optional<Brand> brandOptional = brandRepository.findById(id);

        if(brandOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy hãng");

        }
        Brand brand = brandOptional.get();

        BrandResponse brandResponse = BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();

        return new ApiResponse<>(1, brandResponse);

    }

    @Override
    public ApiResponse<BrandResponse> createBrand(BrandRequest brandRequest) {

        Brand brand = new Brand();
        brand.setName(brandRequest.getName());

        brand = brandRepository.save(brand);

        if(brandRequest.getCategoryIds() != null) {

            List<Category> categoryList = categoryRepository.findAllById(brandRequest.getCategoryIds());

            for(Category category : categoryList) {

                category.getBrands().add(brand);
            }

            categoryRepository.saveAll(categoryList);
        }
        BrandResponse brandResponse = BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();

        return new ApiResponse<>(1, brandResponse);
    }

    @Override
    public ApiResponse<BrandResponse> updateBrand(Long id, BrandRequest brandRequest) {

        Optional<Brand> brandOptional = brandRepository.findById(id);

        if(brandOptional.isEmpty()) {

            throw new RuntimeException("Cập nhật hãng thất bại");

        }
        Brand brand = brandOptional.get();
        brand.setName(brandRequest.getName());

        brand = brandRepository.save(brand);

        BrandResponse brandResponse = BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();

        return new ApiResponse<>(1, brandResponse);

    }

    @Override
    public Message deleteBrand(Long id) {

        Optional<Brand> brandOptional = brandRepository.findById(id);

        if(brandOptional.isEmpty()) {

            throw new RuntimeException("Xóa hãng thất bại");

        }

        brandRepository.delete(brandOptional.get());

        return new Message(1, "Xóa hãng thành công");
    }
}
