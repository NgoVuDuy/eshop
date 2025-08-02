package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.response.BrandResponse;
import com.nvd.electroshop.entity.Brand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BrandMapper {

    public BrandResponse mapToBrandResponse(Brand brand) {

        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }

    public List<BrandResponse> mapToBrandResponseList(List<Brand> brandSet) {

        return brandSet.stream().map(this::mapToBrandResponse).toList();
    }
}
