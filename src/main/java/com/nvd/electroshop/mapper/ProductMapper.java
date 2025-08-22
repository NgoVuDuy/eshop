package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.AttributeProductRequest;
import com.nvd.electroshop.dto.request.ProductRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.elasticsearch.model.ProductSearch;
import com.nvd.electroshop.entity.*;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.repository.BrandRepository;
import com.nvd.electroshop.repository.CategoryRepository;
import com.nvd.electroshop.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductMapper {

    BrandMapper brandMapper;
    CategoryMapper categoryMapper;
    AttributeProductMapper attributeProductMapper;
    ProductImageMapper productImageMapper;

    @Autowired
    BrandRepository brandRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GlobalService globalService;

    @Lazy
    @Autowired
    public void setBrandMapper(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    @Lazy
    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Lazy
    @Autowired
    public void setAttributeProductMapper(AttributeProductMapper attributeProductMapper) {
        this.attributeProductMapper = attributeProductMapper;
    }

    @Lazy
    @Autowired
    public void setProductImageMapper(ProductImageMapper productImageMapper) {
        this.productImageMapper = productImageMapper;
    }

    // response
    public ProductResponse mapToProductResponse(Product product) {
        return this.mapToProductResponse(product, null);
    }

    public List<ProductResponse> mapToProductResponseList(List<Product> productList) {
        return this.mapToProductResponseList(productList, null);
    }

    public ProductResponse mapToProductResponse(Product product, List<String> includes) {

        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .price(globalService.formatCurrency(product.getPrice()))
                .name(product.getName())
                .stockQuantity(product.getStockQuantity())
                .build();

        if (includes != null) {

            if (includes.contains("brand")) {

                BrandResponse brandResponse = brandMapper.mapToBrandResponse(product.getBrand());
                productResponse.setBrand(brandResponse);
            }

            if (includes.contains("categories")) {

                List<Category> categoryList = new ArrayList<>(product.getCategories());
                List<CategoryResponse> categoryResponseList = categoryMapper.mapToCategoryResponseList(categoryList);

                productResponse.setCategories(categoryResponseList);
            }

            if (includes.contains("attributes")) {

                List<AttributeProduct> attributeProductList = product.getAttributeProducts();
                List<AttributeProductResponse> attributeProductResponseList = attributeProductMapper.mapToAttributeProductResponseList(attributeProductList);

                productResponse.setAttributeProducts(attributeProductResponseList);
            }

            if(includes.contains("images")) {

                List<ProductImage> productImageList = product.getProductImages();
                List<ProductImageResponse> productImageResponseList = productImageMapper.mapToProductImageResponseList(productImageList);

                productResponse.setImages(productImageResponseList);
            }
        }

        return productResponse;
    }

    public List<ProductResponse> mapToProductResponseList(List<Product> productList, List<String> includes) {

        return productList.stream().map(product -> this.mapToProductResponse(product, includes)).toList();
    }

    //request

    public Product mapToProduct(ProductRequest productRequest) {

        return this.mapToProduct(productRequest, null);
    }

    public Product mapToProduct(ProductRequest productRequest, Product productDetails) {

        Product product = Objects.requireNonNullElseGet(productDetails, Product::new);

        // Lấy brand
        Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrandId());
        if (brandOptional.isEmpty())
            throw new ResourceNotFoundException("Không tìm thấy hãng");
        Brand brand = brandOptional.get();

        // Lấy categories
        List<Category> categoryList = categoryRepository.findAllById(productRequest.getCategoryIds());

        // Lấy attributes
        List<AttributeProductRequest> attributeProductRequestList = productRequest.getAttributeProductRequests();
        List<AttributeProduct> attributeProductList = attributeProductMapper.mapToAttributeProductList(attributeProductRequestList);

        // Gán product cho mỗi attributeproduct
        attributeProductList.forEach(attributeProduct -> attributeProduct.setProduct(product));

        //set
        product.setName(productRequest.getName());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setPrice(productRequest.getPrice());
        product.setBrand(brand);
        product.setCategories(new HashSet<>(categoryList));

        product.getAttributeProducts().clear();
        product.getAttributeProducts().addAll(attributeProductList);

        return product;
    }
    // search
    public ProductSearch mapToProductSearch(Product product) {

        return mapToProductSearch(product, null);
    }

    public ProductSearch mapToProductSearch(Product product, ProductSearch productSearchDetails) {

        ProductSearch productSearch = Objects.requireNonNullElseGet(productSearchDetails, ProductSearch::new);
        Optional<Brand> brandOptional = brandRepository.findById(product.getBrand().getId());

        if(brandOptional.isEmpty()) {

            throw new ResourceNotFoundException("Không tìm thấy hãng");

        }
        Brand brand = brandOptional.get();

        List<Category> categoryList = new ArrayList<>(product.getCategories());
        List<String> categoryNameList = categoryList.stream().map(Category::getName).toList();

        productSearch.setId(product.getId());
        productSearch.setBrand(brand.getName());
        productSearch.setName(product.getName());
        productSearch.setCategories(categoryNameList);

        return productSearch;
    }
}
