package com.nvd.electroshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeResponse {

    private Long id;
    private String name;
    private String unit;
}
