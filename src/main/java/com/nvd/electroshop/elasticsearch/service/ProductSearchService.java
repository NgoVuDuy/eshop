package com.nvd.electroshop.elasticsearch.service;

import com.nvd.electroshop.elasticsearch.model.ProductSearch;
import org.springframework.data.elasticsearch.annotations.Query;

import java.util.List;

public interface ProductSearchService {

    List<ProductSearch> searchProducts(String keyword);
}
