package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.CategoryRequest;
import com.nvd.electroshop.dto.response.AttributeResponse;
import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.dto.response.CategoryResponse;
import com.nvd.electroshop.dto.response.ProductResponse;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.repository.AttributeRepository;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryMapper {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final AttributeRepository attributeRepository;

    private final ProductMapper productMapper;
    private final BrandMapper brandMapper;
    private final AttributeMapper attributeMapper;

    public CategoryMapper(
            ProductRepository productRepository,
            BrandRepository brandRepository,
            AttributeRepository attributeRepository,
            ProductMapper productMapper,
            BrandMapper brandMapper,
            AttributeMapper attributeMapper
    ) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.attributeRepository = attributeRepository;

        this.productMapper = productMapper;
        this.brandMapper = brandMapper;
        this.attributeMapper = attributeMapper;
    }

    // Trường hợp không truyền includes, mặc định là rỗng
    public CategoryResponse mapToCategoryResponse(Category category) {
        return mapToCategoryResponse(category, null);
    }

    public Category mapToCategory(CategoryRequest categoryRequest) {
        return mapToCategory(categoryRequest, null);
    }


    public CategoryResponse mapToCategoryResponse(Category category, List<String> includes) {

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();

        if(includes != null) {

            if (includes.contains("brands")) {
                List<BrandResponse> brandResponseList = brandMapper.mapToBrandResponseList(new ArrayList<>(category.getBrands()));
                categoryResponse.setBrands(brandResponseList);

            }
            if (includes.contains("products")) {
                List<ProductResponse> productResponseList = productMapper.mapToProductResponseList(new ArrayList<>(category.getProducts()));
                categoryResponse.setProducts(productResponseList);

            }
            if(includes.contains("attributes")) {
                List<AttributeResponse> attributeResponseList = attributeMapper.mapToAttributeResponseList(new ArrayList<>(category.getAttributes()));
                categoryResponse.setAttributes(attributeResponseList);
            }
        }

        return categoryResponse;
    }

    public List<CategoryResponse> mapToCategoryResponseList(List<Category> categoryList, List<String> includes) {

        return categoryList.stream().map(category -> this.mapToCategoryResponse(category, includes)).toList();
    }

    public Category mapToCategory(CategoryRequest categoryRequest, Category categoryDetails) {

        Category category;

        category = Objects.requireNonNullElseGet(categoryDetails, Category::new);

        category.setName(categoryRequest.getName());

        if(categoryRequest.getBrandIds() != null) {

            List<Brand> brandList = brandRepository.findAllById(categoryRequest.getBrandIds());
            Set<Brand> brandSet = new HashSet<>(brandList);
            category.setBrands(brandSet);
        }

        if(categoryRequest.getAttributeIds() != null) {

            List<Attribute> attributeList = attributeRepository.findAllById(categoryRequest.getAttributeIds());
            Set<Attribute> attributeSet = new HashSet<>(attributeList);

            category.setAttributes(attributeSet);
        }

//        if (categoryRequest.getProductIds() != null) {
//
//            List<Product> productList = productRepository.findAllById(categoryRequest.getProductIds());
//            for (Product product : productList) {
//
//                product.getCategories().clear();
//                product.getCategories().add(category);
//            }
//
//            productRepository.saveAll(productList);
//
//        }

        return category;
    }
}