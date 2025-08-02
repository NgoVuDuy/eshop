package com.nvd.electroshop.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private ProductResponse product;
    private int quantity;
    private Double price;
}
