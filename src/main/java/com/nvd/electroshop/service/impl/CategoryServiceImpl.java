package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.CategoryRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.CategoryResponse;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.repository.AttributeRepository;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.repository.CategoryRepository;
import com.nvd.electroshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Override
    public ApiResponse<List<CategoryResponse>>  getAllCategories() {

        List<Category> category = categoryRepository.findAll();

        List<CategoryResponse> categoryResponseIterable = new ArrayList<>();

        category.forEach(c -> {

            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(c.getId());
            categoryResponse.setName(c.getName());

            categoryResponseIterable.add(categoryResponse);
        });

        return new ApiResponse<>(1, categoryResponseIterable);
    }

    @Override
    public ApiResponse<CategoryResponse> getCategoryById(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(categoryOptional.isEmpty()) {

            throw new RuntimeException("Không tìm thấy danh mục sản phẩm");
        }

        Category category = categoryOptional.get();

        CategoryResponse categoryResponse = new CategoryResponse(
                category.getId(),
                category.getName()
        );

        return new ApiResponse<>(1, categoryResponse);
    }

    @Override
    public ApiResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest) {

        Category category = new Category();
        category.setName(categoryRequest.getName());

        //
        if(categoryRequest.getBrandIds() != null) {

            Iterable<Brand> brandIterable = brandRepository.findAllById(categoryRequest.getBrandIds());

            Set<Brand> brandSet = new HashSet<>();
            brandIterable.forEach(brandSet::add);

            category.setBrands(brandSet);
        }

        if(categoryRequest.getAttributeIds() != null) {

            Iterable<Attribute> attributeIterable = attributeRepository.findAllById(categoryRequest.getAttributeIds());

            Set<Attribute> attributeSet = new HashSet<>();
            attributeIterable.forEach(attributeSet::add);

            category.setAttributes(attributeSet);
        }

        category = categoryRepository.save(category);
        CategoryResponse categoryResponse = new CategoryResponse(category.getId(), category.getName());

        return new ApiResponse<>(1, categoryResponse);
    }

    @Override
    public Category updateCategory(Long id, Category categoryDetails) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(categoryOptional.isPresent()) {

            Category category = categoryOptional.get();
            category.setName(categoryDetails.getName());

            return categoryRepository.save(category);

        } else {
            throw new RuntimeException("Cập nhật danh mục sản phẩm thất bại");
        }

    }

    @Override
    public void deleteCategory(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(categoryOptional.isPresent()) {

            categoryRepository.delete(categoryOptional.get());
        } else {
            throw new RuntimeException("Xóa danh mục sản phẩm thất bại");
        }
    }

    @Override
    public ApiResponse<Set<Brand>> getBrandsByCategoryId(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(categoryOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy danh mục sản phẩm");
        }

        Category category = categoryOptional.get();

        Set<Brand> brands = category.getBrands();

        return new ApiResponse<>(1, brands);
    }

    @Override
    public ApiResponse<Set<Attribute>> getAttributesByCategoryId(Long id) {



        return null;
    }
}
