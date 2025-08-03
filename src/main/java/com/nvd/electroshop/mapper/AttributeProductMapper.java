package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.AttributeProductRequest;
import com.nvd.electroshop.dto.response.AttributeProductResponse;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.AttributeProduct;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.repository.AttributeRepository;
import com.nvd.electroshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AttributeProductMapper {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AttributeRepository attributeRepository;

    // response
    public AttributeProductResponse mapToAttributeProductResponse(AttributeProduct attributeProduct) {

        Attribute attribute = attributeProduct.getAttribute();

        return AttributeProductResponse.builder()
                .id(attributeProduct.getId())
                .value(attributeProduct.getValue())
                .name(attribute.getName())
                .unit(attribute.getUnit())
                .build();
    }

    public List<AttributeProductResponse> mapToAttributeProductResponseList(List<AttributeProduct> attributeProductList) {

        return attributeProductList.stream().map(this::mapToAttributeProductResponse).toList();
    }

    // request
    public AttributeProduct mapToAttributeProduct(AttributeProductRequest attributeProductRequest) {

//        Optional<Product> productOptional = productRepository.findById(attributeProductRequest.getProductId());
//
//        if (productOptional.isEmpty()) {
//            throw new ResourceNotFoundException("Không tìm thấy sản phẩm");
//        }

        Optional<Attribute> attributeOptional = attributeRepository.findById(attributeProductRequest.getAttributeId());

        if (attributeOptional.isEmpty()) {
            throw new ResourceNotFoundException("Không tìm thấy thông số");
        }

//        Product product = productOptional.get();
        Attribute attribute = attributeOptional.get();

        return AttributeProduct.builder()
                .value(attributeProductRequest.getValue())
//                .product(product)
                .attribute(attribute)
                .build();
    }

    public List<AttributeProduct> mapToAttributeProductList(List<AttributeProductRequest> attributeProductRequestList) {

        return attributeProductRequestList.stream().map(this::mapToAttributeProduct).toList();
    }
}
