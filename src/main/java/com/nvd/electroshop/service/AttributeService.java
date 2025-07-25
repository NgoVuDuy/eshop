package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.AttributeRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.AttributeResponse;
import com.nvd.electroshop.dto.response.Message;

import java.util.List;

public interface AttributeService {

    public ApiResponse<List<AttributeResponse>> getAllAttributes();
    public ApiResponse<AttributeResponse> getAttributeById(Long id);
    public ApiResponse<AttributeResponse> createAttribute(AttributeRequest attributeRequest);
    public ApiResponse<AttributeResponse> updateAttribute(Long id, AttributeRequest attributeRequest);
    public ApiResponse<Message> deleteAttribute(Long id);
}
