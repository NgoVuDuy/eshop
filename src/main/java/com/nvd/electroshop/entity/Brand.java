package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Một hãng có nhiều danh mục
    @ManyToMany(mappedBy = "brands")
//    @JsonManagedReference
    @JsonIgnoreProperties("brands")
    private Set<Category> categories;

    // Một hãng có nhiều sản phẩm
    @OneToMany(mappedBy = "brand")
    @JsonManagedReference
    List<Product> products;
}
