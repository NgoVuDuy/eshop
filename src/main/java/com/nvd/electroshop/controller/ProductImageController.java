package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.DeleteProductImageRequest;
import com.nvd.electroshop.dto.request.ProductImageRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.ProductImageResponse;
import com.nvd.electroshop.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/product-images")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @PostMapping("uploads")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public SseEmitter uploadProductImages(@ModelAttribute ProductImageRequest productImageRequest) {

        SseEmitter sseEmitter = new SseEmitter(0L);

        executorService.execute(() -> {

                    productImageService.uploadProductImages(productImageRequest, sseEmitter);
                    try {
                        sseEmitter.send(SseEmitter.event().name("Done").data(
                                new Message(1, "Uploaded")
                        ));
                        sseEmitter.complete();

                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
        );

        return sseEmitter;
//        return ResponseEntity.ok(productImageService.uploadProductImage(productImageRequest));
    }

    @DeleteMapping("delete")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Message> deleteProductImage(@RequestBody DeleteProductImageRequest deleteProductImageRequest) {

        return ResponseEntity.ok(productImageService.deleteProductImage(deleteProductImageRequest));
    }

    @DeleteMapping("delete-all/{productId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Message> deleteAllProductImages(@PathVariable Long productId) {

        return ResponseEntity.ok(productImageService.deleteAllProductImages(productId));
    }
}
