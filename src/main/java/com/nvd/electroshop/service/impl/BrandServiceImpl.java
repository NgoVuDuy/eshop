package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public Iterable<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id) {

        Optional<Brand> brand = brandRepository.findById(id);

        if(brand.isPresent()) {
            return brand.get();
        } else {
            throw new RuntimeException("Không tìm thấy hãng");
        }

    }

    @Override
    public Brand createBrand(Brand brand) {

        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Long id, Brand brandDetails) {

        Optional<Brand> brandOptional = brandRepository.findById(id);

        if(brandOptional.isPresent()) {

            Brand brand = brandOptional.get();

            brand.setName(brandDetails.getName());

            return brand;
        } else {
            throw new RuntimeException("Cập nhật hãng thất bại");
        }
    }

    @Override
    public void deleteBrand(Long id) {

        Optional<Brand> brandOptional = brandRepository.findById(id);

        if(brandOptional.isPresent()) {

            brandRepository.delete(brandOptional.get());

        } else {
            throw new RuntimeException("Xóa hãng thất bại");
        }
    }
}
