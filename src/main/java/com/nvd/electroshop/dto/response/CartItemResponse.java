package com.nvd.electroshop.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {

    private String name;
    private Double price;
    private Long stockQuantity;
    private int quantity;
}