package com.nvd.electroshop.service;

import com.nvd.electroshop.entity.Brand;

import java.util.Optional;

public interface BrandService {

    public Iterable<Brand> getAllBrands();
    public Brand getBrandById(Long id);
    public Brand createBrand(Brand brand);
    public Brand updateBrand(Long id, Brand brand);
    public void deleteBrand(Long id);
}
