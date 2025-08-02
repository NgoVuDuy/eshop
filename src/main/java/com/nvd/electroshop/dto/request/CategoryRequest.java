package com.nvd.electroshop.dto.request;


import com.nvd.electroshop.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {

    private Long id;
    private String name;

    private List<Long> brandIds;
    private List<Long> attributeIds;
//    private List<Long> productIds;
}