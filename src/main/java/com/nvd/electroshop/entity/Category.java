package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;

    // Một danh mục có nhiều hãng
    @ManyToMany
    @JsonIgnoreProperties("categories")
    @JoinTable(
            name = "category_brand",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private Set<Brand> brands;

    // Một danh mục gồm nhiều sản phẩm
    @ManyToMany(mappedBy = "categories")
    @JsonIgnoreProperties("categories")
    private Set<Product> products;
}
