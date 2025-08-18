package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.AttributeProductRequest;
import com.nvd.electroshop.dto.request.ProductRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.entity.*;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.*;
import com.nvd.electroshop.repository.AttributeProductRepository;
import com.nvd.electroshop.repository.AttributeRepository;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;
    private final ReviewMapper reviewMapper;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;
    private final AttributeProductMapper attributeProductMapper;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductMapper productMapper,
            ReviewMapper reviewMapper,
            BrandMapper brandMapper,
            CategoryMapper categoryMapper,
            AttributeProductMapper attributeProductMapper
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.reviewMapper = reviewMapper;
        this.brandMapper = brandMapper;
        this.categoryMapper = categoryMapper;
        this.attributeProductMapper = attributeProductMapper;
    }

    @Override
    @Cacheable(value = "products", key = "'all'")
    public ApiResponse<List<ProductResponse>> getAllProducts(List<String> includes) {

        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponseList = productMapper.mapToProductResponseList(products, includes);

        return new ApiResponse<>(1, productResponseList);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ApiResponse<ProductResponse> getProductById(Long id, List<String> includes) {

        Product product = getProduct(id);
        ProductResponse productResponse = productMapper.mapToProductResponse(product, includes);

        return new ApiResponse<>(1, productResponse);
    }

    @Override
    public ApiResponse<ProductResponse> createProduct(ProductRequest productRequest) {

        Product product = productMapper.mapToProduct(productRequest);
        product = productRepository.save(product);

        ProductResponse productResponse = productMapper.mapToProductResponse(product);

        return new ApiResponse<>(1, productResponse);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "products", key = "'all'"),
            @CacheEvict(value = "products", key = "#id")
    })
    public ApiResponse<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {

        Product product = getProduct(id);
        product = productMapper.mapToProduct(productRequest, product);

        product = productRepository.save(product);

        ProductResponse productResponse = productMapper.mapToProductResponse(product);
        return new ApiResponse<>(1, productResponse);
    }

    @Override
    public ApiResponse<ProductResponse> partialUpdateProduct(Long id, Map<String, Object> requests) {

        return null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "products", key = "'all'"),
            @CacheEvict(value = "products", key = "#id")
    })
    public Message deleteProduct(Long id) {

        Product product = getProduct(id);
        productRepository.delete(product);

        return new Message(1, "Xóa sản phẩm thành công");
    }

    @Override
    public ApiResponse<List<ReviewResponse>> getReviewsByProductId(Long id) {

        Product product = getProduct(id);
        List<Review> reviewList = product.getReviews();

        List<ReviewResponse> reviewResponseList = reviewMapper.mapToReviewResponseList(reviewList);
        return new ApiResponse<>(1, reviewResponseList);
    }

    @Override
    public ApiResponse<List<AttributeProductResponse>> getAttributeProductsById(Long id) {

        Product product = getProduct(id);
        List<AttributeProduct> attributeProductList = product.getAttributeProducts();

        List<AttributeProductResponse> attributeProductResponseList
                = attributeProductMapper.mapToAttributeProductResponseList(attributeProductList);

        return new ApiResponse<>(1, attributeProductResponseList);
    }

    @Override
    public ApiResponse<List<CategoryResponse>> getCategoriesByProductId(Long id) {

        Product product = getProduct(id);
        List<Category> categoryList = new ArrayList<>(product.getCategories());

        List<CategoryResponse> categoryResponseList = categoryMapper.mapToCategoryResponseList(categoryList);
        return new ApiResponse<>(1, categoryResponseList);
    }

    @Override
    public ApiResponse<BrandResponse> getBrandByProductId(Long id) {

        Product product = getProduct(id);
        Brand brand = product.getBrand();

        BrandResponse brandResponse = brandMapper.mapToBrandResponse(brand);

        return new ApiResponse<>(1, brandResponse);
    }

    private Product getProduct(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()) {

            throw new ResourceNotFoundException("Không tìm thấy sản phẩm");
        }

        return productOptional.get();
    }
}