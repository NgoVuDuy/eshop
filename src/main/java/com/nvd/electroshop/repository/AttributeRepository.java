package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
}
