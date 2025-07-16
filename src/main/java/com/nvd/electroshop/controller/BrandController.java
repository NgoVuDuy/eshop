package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.BrandRequest;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping
    public Iterable<Brand> getAllBrands() {

        return brandService.getAllBrands();
    }

    @GetMapping("{id}")
    public Brand getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    @PostMapping
    public Brand createBrand(@RequestBody BrandRequest brandRequest) {

        return brandService.createBrand(brandRequest);
    }

    @PutMapping
    public Brand updateBrand(@PathVariable Long id, @RequestBody Brand brand) {

        return brandService.updateBrand(id, brand);
    }
    @DeleteMapping("{id}")
    public void deleteBrand(@PathVariable Long id) {

        brandService.deleteBrand(id);
    }
}
