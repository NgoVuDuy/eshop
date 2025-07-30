package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nhiều chi tiết đơn hàng thuộc về một đơn hàng
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    // Nhiều chi tiết đơn hàng thuộc về một sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
}