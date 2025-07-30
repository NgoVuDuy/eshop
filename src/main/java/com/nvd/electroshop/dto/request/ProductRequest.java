package com.nvd.electroshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private String name;
    private Long stockQuantity;
    private Double price;

    private Long brandId;
//    private List<Long> categoryIds;
    private List<AttributeProductRequest> attributeProductRequests;
//    private List<String> imageUrl;

}
