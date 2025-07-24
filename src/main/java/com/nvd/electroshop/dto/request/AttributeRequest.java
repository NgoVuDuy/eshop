package com.nvd.electroshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeRequest {

    private Long id;
    private String name;
    private String unit;

    private List<Long> categoryIds;

}
