package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
