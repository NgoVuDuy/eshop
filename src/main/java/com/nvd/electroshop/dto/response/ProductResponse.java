package com.nvd.electroshop.dto.response;

import com.nvd.electroshop.entity.AttributeProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String name;
    private Long stockQuantity;
    private Double price;

    private BrandResponse brandResponse;
    private List<AttributeProductResponse> attributeProductResponses;
}
