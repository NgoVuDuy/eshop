package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.AttributeProductRequest;
import com.nvd.electroshop.dto.request.ProductRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.entity.AttributeProduct;
import com.nvd.electroshop.entity.Brand;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.repository.AttributeProductRepository;
import com.nvd.electroshop.repository.AttributeRepository;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    AttributeProductRepository attributeProductRepository;

    @Override
    public ApiResponse<List<ProductResponse>> getAllProducts() {

        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponseList = new ArrayList<>();

        products.forEach(product -> {


            Brand brand = product.getBrand();
            BrandResponse brandResponse = BrandResponse.builder()
                    .id(brand.getId())
                    .name(brand.getName())
                    .build();

            List<AttributeProduct> attributeProductList = product.getAttributeProducts();
            List<AttributeProductResponse> attributeProductResponses = new ArrayList<>();

            for (AttributeProduct attributeProduct : attributeProductList) {

                AttributeProductResponse attributeProductResponse = AttributeProductResponse.builder()
                        .name(attributeProduct.getAttribute().getName())
                        .unit(attributeProduct.getAttribute().getUnit())
                        .value(attributeProduct.getValue())
                        .build();
                attributeProductResponses.add(attributeProductResponse);
            }


            ProductResponse productResponse = ProductResponse.builder()
                    .name(product.getName())
                    .stockQuantity(product.getStockQuantity())
                    .price(product.getPrice())
                    .brandResponse(brandResponse)
                    .attributeProductResponses(attributeProductResponses)
                    .build();

            productResponseList.add(productResponse);
        });

        return new ApiResponse<>(1, productResponseList);
    }

    @Override
    public ApiResponse<ProductResponse> createProduct(ProductRequest productRequest) {

        // Brand
        Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrandId());

        if(brandOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy hãng");
        }

        Brand brand = brandOptional.get();

        Product product = Product.builder()
                .name(productRequest.getName())
                .stockQuantity(productRequest.getStockQuantity())
                .price(productRequest.getPrice())
                .brand(brand)
                .build();

        product = productRepository.save(product);

        List<AttributeProductRequest> attributeProductRequests = productRequest.getAttributeProductRequests();
        List<AttributeProduct> attributeProductList = new ArrayList<>();

        for (AttributeProductRequest attributeProductRequest : attributeProductRequests) {

            Optional<Attribute> attributeOptional = attributeRepository.findById(attributeProductRequest.getAttributeId());

            if (attributeOptional.isEmpty()) {
                throw new RuntimeException("Không tìm thấy thông số");
            }

            Attribute attribute = attributeOptional.get();

            AttributeProduct attributeProduct = AttributeProduct.builder()

                    .product(product)
                    .attribute(attribute)
                    .value(attributeProductRequest.getValue())
                    .build();

            attributeProductList.add(attributeProduct);
        }

        product.setAttributeProducts(attributeProductList);
        attributeProductRepository.saveAll(attributeProductList);

//        attributeProductRepository.saveAll(attributeProductList);
        product = productRepository.save(product);

        BrandResponse brandResponse = BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();

        List<AttributeProduct> attributeProducts = product.getAttributeProducts();
        List<AttributeProductResponse> attributeProductResponses = new ArrayList<>();

        for (AttributeProduct attributeProduct : attributeProducts) {

            AttributeProductResponse attributeProductResponse = AttributeProductResponse.builder()
                    .name(attributeProduct.getAttribute().getName())
                    .unit(attributeProduct.getAttribute().getUnit())
                    .value(attributeProduct.getValue())
                    .build();
            attributeProductResponses.add(attributeProductResponse);
        }

        ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .stockQuantity(product.getStockQuantity())
                .price(product.getPrice())
                .brandResponse(brandResponse)
                .attributeProductResponses(attributeProductResponses)
                .build();

        return new ApiResponse<>(1, productResponse);
    }

    @Override
    public ApiResponse<ProductResponse> getProductById(Long id) {
        return null;
    }

    @Override
    public ApiResponse<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return null;
    }

    @Override
    public Message deleteProduct(Long id) {
        return null;
    }
}
