package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.repository.CategoryRepository;
import com.nvd.electroshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

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
    public Category createCategory(Category category) {

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
