package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.dto.response.ProductResponse;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    BrandMapper brandMapper;

    public ProductResponse mapToProductResponse(Product product) {

        Brand brand = product.getBrand();
        BrandResponse brandResponse = brandMapper.mapToBrandResponse(brand);

        return ProductResponse.builder()
                .brandResponse(brandResponse)
                .price(product.getPrice())
                .name(product.getName())
                .stockQuantity(product.getStockQuantity())
                .build();

    }
}
