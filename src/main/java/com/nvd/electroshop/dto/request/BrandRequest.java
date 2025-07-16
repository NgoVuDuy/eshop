package com.nvd.electroshop.dto.request;

import com.nvd.electroshop.entity.Brand;
import lombok.Data;

import java.util.List;

@Data
public class BrandRequest {

    private Brand brand;
    private List<Long> categoryIds;
}
