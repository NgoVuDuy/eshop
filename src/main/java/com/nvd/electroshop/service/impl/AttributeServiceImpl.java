package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.AttributeRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.AttributeResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.entity.ProductImage;
import com.nvd.electroshop.repository.AttributeRepository;
import com.nvd.electroshop.repository.CategoryRepository;
import com.nvd.electroshop.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ApiResponse<List<AttributeResponse>> getAllAttributes() {

        List<Attribute> attributeList = attributeRepository.findAll();

        List<AttributeResponse> attributeResponses = attributeList.stream().map(attribute ->

                new AttributeResponse(attribute.getId(), attribute.getName(), attribute.getUnit())

                ).toList();

        return new ApiResponse<>(1, attributeResponses);
    }

    @Override
    public ApiResponse<AttributeResponse> getAttributeById(Long id) {

        return null;
    }

    @Override
    public ApiResponse<AttributeResponse> createAttribute(AttributeRequest attributeRequest) {

        Attribute attribute = new Attribute();
        attribute.setName(attributeRequest.getName());
        attribute.setUnit(attributeRequest.getUnit());

        attribute = attributeRepository.save(attribute);

        // Kiểu tra có danh sách các category id không
        if (attributeRequest.getCategoryIds() != null) {

            List<Category> categoryList = categoryRepository.findAllById(attributeRequest.getCategoryIds());

            for (Category category : categoryList) {

                category.getAttributes().add(attribute);
            }

            categoryRepository.saveAll(categoryList);

//            Set<Category> categorySet = new HashSet<>(categoryList);
//            attribute.setCategories(categorySet);
        }

        AttributeResponse attributeResponse = new AttributeResponse(attribute.getId(), attribute.getName(), attribute.getUnit());

        return new ApiResponse<>(1, attributeResponse);
    }

    @Override
    public ApiResponse<AttributeResponse> updateAttribute(Long id, AttributeRequest attributeRequest) {
        return null;
    }

    @Override
    public ApiResponse<Message> deleteAttribute(Long id) {
        return null;
    }
}
