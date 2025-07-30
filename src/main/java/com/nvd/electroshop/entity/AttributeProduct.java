package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "attribute_product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quan hệ tới bảng product
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    // Quan hệ tới bảng attribute
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    @JsonBackReference
    private Attribute attribute;

    private Double value;
}
