package com.nvd.electroshop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String public_id;
    private String url;

    // Một hay nhiều ảnh thuộc về một sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
