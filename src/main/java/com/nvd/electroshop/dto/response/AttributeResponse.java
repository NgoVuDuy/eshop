package com.nvd.electroshop.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeResponse {

    private Long id;
    private String name;
    private String unit;
}
