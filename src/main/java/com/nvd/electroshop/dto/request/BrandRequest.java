package com.nvd.electroshop.dto.request;

import com.nvd.electroshop.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandRequest {

    private Long id;
    private String name;

//    private List<Long> categoryIds;
}
