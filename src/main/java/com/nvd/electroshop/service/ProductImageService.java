package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.DeleteProductImageRequest;
import com.nvd.electroshop.dto.request.ProductImageRequest;
import com.nvd.electroshop.dto.response.Message;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface ProductImageService {

    public void uploadProductImages(ProductImageRequest productImageRequest, SseEmitter sseEmitter);
    public Message deleteProductImage(DeleteProductImageRequest deleteProductImageRequest);
    public Message deleteAllProductImages(Long productId);
}
