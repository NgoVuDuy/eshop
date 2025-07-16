package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    // Một hay nhiều sản phẩm thuộc về một hãng
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "brand_id")
    Brand brand;
}
