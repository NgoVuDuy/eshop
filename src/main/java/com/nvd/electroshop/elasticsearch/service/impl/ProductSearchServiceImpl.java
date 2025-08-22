package com.nvd.electroshop.elasticsearch.service.impl;

import com.nvd.electroshop.elasticsearch.model.ProductSearch;
import com.nvd.electroshop.elasticsearch.repository.ProductSearchRepository;
import com.nvd.electroshop.elasticsearch.service.ProductSearchService;
import com.nvd.electroshop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductSearchRepository productSearchRepository;

    public ProductSearchServiceImpl(ProductSearchRepository productSearchRepository) {
        this.productSearchRepository = productSearchRepository;
    }

    @Override
    public List<ProductSearch> searchProducts(String keyword) {

        List<ProductSearch> productSearches =   productSearchRepository.searchAllFields(keyword);

        return productSearches;
    }
}
