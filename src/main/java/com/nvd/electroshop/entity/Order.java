package com.nvd.electroshop.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // Trạng thái đơn hàng
    private Double total; // Tổng giá
    private LocalDateTime order_datetime; // Thời gian đặt

    // Một đơn hàng thuộc về một người dùng
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // Một đơn hàng gồm nhiều chi tiết đơn hàng
    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderItem> orderItems;
}
