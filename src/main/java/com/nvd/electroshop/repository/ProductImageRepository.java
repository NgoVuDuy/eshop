package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    public Optional<ProductImage> findByPublicId(String publicId);
}
