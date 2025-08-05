package com.nvd.electroshop.dto.request;

import com.nvd.electroshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequest {

    private Long productId;
    private List<MultipartFile> productImageFiles;
}
