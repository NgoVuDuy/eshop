package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.response.AttributeResponse;
import com.nvd.electroshop.entity.Attribute;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttributeMapper {

    public AttributeResponse mapToAttributeResponse(Attribute attribute) {

        return AttributeResponse.builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .unit(attribute.getUnit())
                .build();
    }

    public List<AttributeResponse> mapToAttributeResponseList(List<Attribute> attributeList) {

        return attributeList.stream().map(this::mapToAttributeResponse).toList();
    }
}
