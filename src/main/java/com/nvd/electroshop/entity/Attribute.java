package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "attributes")
// Bảng lưu các thông số kĩ thuật của sản phẩm
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String unit;

    // Một thuộc tính thuộc về nhiều danh mục
    @ManyToMany(mappedBy = "attributes")
    @JsonIgnoreProperties("attributes")
    private Set<Category> categories;

    // Quan hệ tới bảng liên kết attribute_product
    @OneToMany(mappedBy = "attribute")
    @JsonManagedReference
    private List<AttributeProduct> attributeProducts;
}
