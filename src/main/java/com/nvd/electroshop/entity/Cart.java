package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Một giỏ hàng thuộc về một người dùng
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @JsonBackReference
    private User user;

    // Một giỏ hàng gồm nhiều chi tiết giỏ hàng
    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    private List<CartItem> cartItems;

}
