package com.nvd.electroshop.controller;

import com.nvd.electroshop.component.RabbitMQProducer;
import com.nvd.electroshop.dto.request.DeleteProductImageRequest;
import com.nvd.electroshop.dto.request.ProductImageMessage;
import com.nvd.electroshop.dto.request.ProductImageRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.ProductImageResponse;
import com.nvd.electroshop.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/product-images")
public class ProductImageController {

    private final ProductImageService productImageService;
    private final RabbitMQProducer rabbitMQProducer;

    public ProductImageController(ProductImageService productImageService, RabbitMQProducer rabbitMQProducer) {
        this.productImageService = productImageService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

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

    @PostMapping("mq-uploads")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Message> uploadProductImagesMQ(@ModelAttribute ProductImageRequest productImageRequest) throws IOException{


        List<ProductImageMessage> messages = new ArrayList<>();

        for (MultipartFile file : productImageRequest.getProductImageFiles()) {
            String base64 = Base64.getEncoder().encodeToString(file.getBytes());
            String fileName = UUID.randomUUID().toString().substring(0, 5);

            ProductImageMessage msg = ProductImageMessage.builder()
                    .productId(productImageRequest.getProductId())
                    .fileName(fileName)
                    .fileData(base64)
                    .build();

            messages.add(msg);
            rabbitMQProducer.uploadProductImage(msg);
        }

        return ResponseEntity.ok(new Message(1,"Đang chờ upload ảnh"));
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
