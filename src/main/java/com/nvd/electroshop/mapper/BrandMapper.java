package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.BrandRequest;
import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.dto.response.CategoryResponse;
import com.nvd.electroshop.dto.response.ProductResponse;
import com.nvd.electroshop.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BrandMapper {

    private CategoryMapper categoryMapper;
    private ProductMapper productMapper;

    @Lazy
    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Lazy
    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // reponse
    public BrandResponse mapToBrandResponse(Brand brand) {

        return this.mapToBrandResponse(brand, null);
    }

    public List<BrandResponse> mapToBrandResponseList(List<Brand> brandList) {

        return this.mapToBrandResponseList(brandList, null);
    }

    public BrandResponse mapToBrandResponse(Brand brand, List<String> includes) {

        BrandResponse brandResponse = BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();

        if(includes != null) {

            if (includes.contains("categories")) {
                List<CategoryResponse> categoryResponseList = categoryMapper.mapToCategoryResponseList(new ArrayList<>(brand.getCategories()));
                brandResponse.setCategories(categoryResponseList);
            }

            if (includes.contains("products")) {
                List<ProductResponse> productResponseList = productMapper.mapToProductResponseList(new ArrayList<>(brand.getProducts()));
                brandResponse.setProducts(productResponseList);
            }
        }

        return brandResponse;
    }

    public List<BrandResponse> mapToBrandResponseList(List<Brand> brandList, List<String> includes) {

        return brandList.stream().map(brand -> this.mapToBrandResponse(brand, includes)).toList();
    }

    //request
    public Brand mapToBrand(BrandRequest brandRequest) {
        return mapToBrand(brandRequest, null);
    }
    public Brand mapToBrand(BrandRequest brandRequest, Brand brandDetails) {

        Brand brand = Objects.requireNonNullElseGet(brandDetails, Brand::new);

        brand.setName(brandRequest.getName());

        return brand;

    }
}
