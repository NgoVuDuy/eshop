package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.AttributeRequest;
import com.nvd.electroshop.dto.response.AttributeResponse;
import com.nvd.electroshop.entity.Attribute;
import org.springframework.stereotype.Component;
import org.w3c.dom.Attr;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class AttributeMapper {

    // response
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

    // request
    public Attribute mapToAttribute(AttributeRequest attributeRequest) {

        return this.mapToAttribute(attributeRequest, null);
    }

    public Attribute mapToAttribute(AttributeRequest attributeRequest, Attribute attributeDetails) {

        Attribute attribute = Objects.requireNonNullElseGet(attributeDetails, Attribute::new);

        attribute.setName(attributeRequest.getName());
        attribute.setUnit(attributeRequest.getUnit());

        return attribute;
    }

    public Attribute mapToAttribute(Map<String, Object> requests, Attribute attributeDetails) {

        requests.forEach((key, value) -> {

            switch (key) {
                case "name":
                    attributeDetails.setName((String) value);
                    break;
                case "unit":
                    attributeDetails.setUnit((String) value);
            }
        });

        return attributeDetails;
    }
}
