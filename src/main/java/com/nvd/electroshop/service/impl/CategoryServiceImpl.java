package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.CategoryRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.AttributeMapper;
import com.nvd.electroshop.mapper.BrandMapper;
import com.nvd.electroshop.mapper.CategoryMapper;
import com.nvd.electroshop.repository.AttributeRepository;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.repository.CategoryRepository;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private AttributeMapper attributeMapper;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ApiResponse<List<CategoryResponse>>  getAllCategories(List<String> includes) {

        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> categoryResponseList = categoryMapper.mapToCategoryResponseList(categoryList, includes);

        return new ApiResponse<>(1, categoryResponseList);
    }

    @Override
    public ApiResponse<CategoryResponse> getCategoryById(Long id, List<String> includes) {

        Category category = getCategory(id);
        CategoryResponse categoryResponse = categoryMapper.mapToCategoryResponse(category, includes);

        return new ApiResponse<>(1, categoryResponse);
    }

    @Override
    public ApiResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest) {

        Category category = categoryMapper.mapToCategory(categoryRequest);

        category = categoryRepository.save(category);
        CategoryResponse categoryResponse = categoryMapper.mapToCategoryResponse(category);

        return new ApiResponse<>(1, categoryResponse);
    }

    @Override
    public ApiResponse<CategoryResponse> updateCategory(Long id, CategoryRequest categoryRequest) {

        Category category = getCategory(id);
        category = categoryMapper.mapToCategory(categoryRequest, category);

        category = categoryRepository.save(category);
        CategoryResponse categoryResponse = categoryMapper.mapToCategoryResponse(category);

        return new ApiResponse<>(1, categoryResponse);
    }

    @Override
    public Message deleteCategory(Long id) {

        Category category = getCategory(id);
        categoryRepository.delete(category);

        return new Message(1, "Xóa danh mục thành công");
    }

    @Override
    public ApiResponse<List<BrandResponse>> getBrandsByCategoryId(Long id) {

        Category category = getCategory(id);

        List<Brand> brandList = new ArrayList<>(category.getBrands());
        List<BrandResponse> brandResponseList = brandMapper.mapToBrandResponseList(brandList);

        return new ApiResponse<>(1, brandResponseList);
    }

    @Override
    public ApiResponse<List<AttributeResponse>> getAttributesByCategoryId(Long id) {

        Category category = getCategory(id);

        List<Attribute> attributeList = new ArrayList<>(category.getAttributes());
        List<AttributeResponse> attributeResponseList = attributeMapper.mapToAttributeResponseList(attributeList);

        return new ApiResponse<>(1, attributeResponseList);
    }
    // ?

    private Category getCategory(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()) {

            throw new ResourceNotFoundException("Không tìm thấy danh mục sản phẩm");
        }

        return categoryOptional.get();
    }
}