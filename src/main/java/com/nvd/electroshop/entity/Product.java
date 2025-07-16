package com.nvd.electroshop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;

    // Một sản phẩm thuộc về nhiều danh mục
    // Một sản phẩm thuộc về một hãng
}
