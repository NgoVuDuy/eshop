package com.nvd.electroshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

    private Long id;
    private String name;
    private String price;
    private Long stockQuantity;

    private BrandResponse brand;
    private List<AttributeProductResponse> attributeProducts;
    private List<CategoryResponse> categories;
    private List<ReviewResponse> reviews;
    private List<ProductImageResponse> images;
}
