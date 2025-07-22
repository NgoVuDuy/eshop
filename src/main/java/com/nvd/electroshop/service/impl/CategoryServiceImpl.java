package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.CategoryRequest;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.repository.CategoryRepository;
import com.nvd.electroshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BrandRepository brandRepository;

    @Override
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(categoryOptional.isPresent()) {

            return categoryOptional.get();
        } else {
            throw new RuntimeException("Không tìm thấy danh mục sản phẩm");
        }

    }

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {

        Category category = new Category();
        category.setName(categoryRequest.getCategory().getName());

        //
        if(categoryRequest.getBrandIds() != null) {

            Iterable<Brand> brandIterable = brandRepository.findAllById(categoryRequest.getBrandIds());

            Set<Brand> brandSet = new HashSet<>();
            brandIterable.forEach(brandSet::add);

            category.setBrands(brandSet);
        }

        return categoryRepository.save(category);
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
}
