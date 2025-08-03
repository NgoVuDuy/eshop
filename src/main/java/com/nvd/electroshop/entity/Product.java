package com.nvd.electroshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long stockQuantity;
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

    // Quan hệ tới bảng liên kết attribute_product
    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AttributeProduct> attributeProducts = new ArrayList<>();

    // Một sản phẩm có nhiều ảnh
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

    // Một sản phẩm gồm nhiều chi tiết giỏ hàng
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems;

    // Một sản phẩm thuộc về nhiều chi tiết đơn hàng
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    // Một sản phẩm có nhiều đánh giá
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews;

    // Một sản phẩm có nhiều lượt yêu thích
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Wishlist> wishlists;
}