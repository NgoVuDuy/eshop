package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;



    // Một hay nhiều sản phẩm thuộc về một hãng
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "brand_id")
    Brand brand;

    // Một sản phẩm thuộc về nhiều danh mục
    @ManyToMany
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("products")
    private Set<Category> categories;
}