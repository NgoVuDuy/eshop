package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.response.ProductImageResponse;
import com.nvd.electroshop.entity.ProductImage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductImageMapper {

    //response
    public ProductImageResponse mapToProductImageResponse(ProductImage productImage) {

        return ProductImageResponse.builder()
                .id(productImage.getId())
                .public_id(productImage.getPublicId())
                .url(productImage.getUrl())
                .build();
    }

    //request
    public List<ProductImageResponse> mapToProductImageResponseList(List<ProductImage> productImageList) {

        return productImageList.stream().map(this::mapToProductImageResponse).toList();
    }
}
