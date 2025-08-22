package com.nvd.electroshop.elasticsearch.repository;


import com.nvd.electroshop.elasticsearch.model.ProductSearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearch, Long> {

    @Query("""
                 {
                    "multi_match": {
                        "query": "?0",
                        "fields": ["name", "brand", "categories"],
                        "fuzziness": "AUTO"
                    }
                 }
            """)
    List<ProductSearch> searchAllFields(String keyword);
}
