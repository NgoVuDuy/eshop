package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    public Optional<ProductImage> findByPublicId(String publicId);
    public int deleteByPublicId(String publicId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductImage pi WHERE pi.publicId IN :publicIds")
    public int deleteByPublicIdIn(List<String> publicIds);
}
