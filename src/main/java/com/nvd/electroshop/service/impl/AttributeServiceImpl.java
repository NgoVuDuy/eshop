package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.AttributeRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.AttributeResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.Category;
import com.nvd.electroshop.entity.ProductImage;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.AttributeMapper;
import com.nvd.electroshop.repository.AttributeRepository;
import com.nvd.electroshop.repository.CategoryRepository;
import com.nvd.electroshop.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeMapper attributeMapper;

    public AttributeServiceImpl(AttributeRepository attributeRepository, AttributeMapper attributeMapper) {
        this.attributeRepository = attributeRepository;
        this.attributeMapper = attributeMapper;
    }

    @Override
    public ApiResponse<List<AttributeResponse>> getAllAttributes() {

        List<Attribute> attributeList = attributeRepository.findAll();
        List<AttributeResponse> attributeResponses = attributeMapper.mapToAttributeResponseList(attributeList);

        return new ApiResponse<>(1, attributeResponses);
    }

    @Override
    public ApiResponse<AttributeResponse> getAttributeById(Long id) {

        Attribute attribute = getAttribute(id);
        AttributeResponse attributeResponse = attributeMapper.mapToAttributeResponse(attribute);

        return new ApiResponse<>(1, attributeResponse);
    }

    @Override
    public ApiResponse<AttributeResponse> createAttribute(AttributeRequest attributeRequest) {

        Attribute attribute = attributeMapper.mapToAttribute(attributeRequest);
        attribute = attributeRepository.save(attribute);

        AttributeResponse attributeResponse = attributeMapper.mapToAttributeResponse(attribute);

        return new ApiResponse<>(1, attributeResponse);
    }

    @Override
    public ApiResponse<AttributeResponse> updateAttribute(Long id, AttributeRequest attributeRequest) {

        Attribute attribute = getAttribute(id);

        attribute = attributeMapper.mapToAttribute(attributeRequest, attribute);
        attribute = attributeRepository.save(attribute);

        AttributeResponse attributeResponse = attributeMapper.mapToAttributeResponse(attribute);
        return new ApiResponse<>(1, attributeResponse);
    }

    @Override
    public ApiResponse<AttributeResponse> partialUpdateAttribute(Long id, Map<String, Object> requests) {

        Attribute attribute = getAttribute(id);

        attribute = attributeMapper.mapToAttribute(requests, attribute);
        attribute = attributeRepository.save(attribute);

        AttributeResponse attributeResponse = attributeMapper.mapToAttributeResponse(attribute);

        return new ApiResponse<>(1, attributeResponse);
    }

    @Override
    public Message deleteAttribute(Long id) {

        Attribute attribute = getAttribute(id);
        attributeRepository.delete(attribute);

        return new Message(1, "Xóa thông số thành công");
    }

    private Attribute getAttribute(Long id) {

        Optional<Attribute> attributeOptional = attributeRepository.findById(id);

        if (attributeOptional.isEmpty()) {

            throw new ResourceNotFoundException("Không tìm thấy thông số");
        }

        return attributeOptional.get();
    }
}
