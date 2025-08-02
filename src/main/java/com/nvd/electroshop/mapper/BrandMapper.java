package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public BrandResponse mapToBrandResponse(Brand brand) {

        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
