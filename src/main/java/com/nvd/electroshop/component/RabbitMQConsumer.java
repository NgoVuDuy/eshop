package com.nvd.electroshop.component;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nvd.electroshop.constant.RabbitMQ;
import com.nvd.electroshop.dto.request.ProductImageMessage;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.ProductImage;
import com.nvd.electroshop.mapper.ProductImageMapper;
import com.nvd.electroshop.repository.ProductImageRepository;
import com.nvd.electroshop.service.GlobalService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class RabbitMQConsumer {

    private final Cloudinary cloudinary;
    private final ProductImageRepository productImageRepository;
    private final GlobalService globalService;
    private final String folder = "eshop/products";

    public RabbitMQConsumer(
            Cloudinary cloudinary,
            ProductImageRepository productImageRepository,
            GlobalService globalService
    ) {
        this.cloudinary = cloudinary;
        this.productImageRepository = productImageRepository;
        this.globalService = globalService;
    }

    @RabbitListener(queues = RabbitMQ.PRODUCT_QUEUE)
    public void uploadProductImage(ProductImageMessage message) {

        try {
            Product product = globalService.getProductById(message.getProductId());

            byte[] data = Base64.getDecoder().decode(message.getFileData());

            // uploade lên cloudinary
            String url = cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", message.getFileName(),
                    "folder", folder
            )).get("url").toString();

            // lưu vào db
            ProductImage productImage = ProductImage.builder()
                    .url(url)
                    .publicId("products/" + message.getFileName())
                    .product(product)
                    .build();

            productImage = productImageRepository.save(productImage);

            System.out.println("Upload done for: " + message.getFileName());

        } catch (Exception e) {

            System.err.println("Upload failed for " + message.getFileName());
        }
    }
}