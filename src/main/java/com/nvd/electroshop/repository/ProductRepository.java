package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
