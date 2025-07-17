package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String phone;
    private String address;
    private LocalDate birthDate;

    private boolean isAdmin;

    // Một người dùng có một giỏ hàng
    @OneToOne(mappedBy = "user")
    @JsonManagedReference
    private Cart cart;

    // Một người dùng có nhiều đơn hàng
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Order> orders;

    // Một người dùng có nhiều đánh giá sản phẩm
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Reviews> reviews;

    // Một người dùng yêu thích nhiều sản phẩm
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Wishlist> wishlists;
}
