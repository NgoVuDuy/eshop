package com.nvd.electroshop.elasticsearch.repository;


import com.nvd.electroshop.elasticsearch.model.ProductSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearch, Long> {
}
