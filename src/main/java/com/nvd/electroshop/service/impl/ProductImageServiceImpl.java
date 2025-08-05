package com.nvd.electroshop.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nvd.electroshop.dto.request.DeleteProductImageRequest;
import com.nvd.electroshop.dto.request.ProductImageRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.ProductImageResponse;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.ProductImage;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.ProductImageMapper;
import com.nvd.electroshop.repository.ProductImageRepository;
import com.nvd.electroshop.service.GlobalService;
import com.nvd.electroshop.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private GlobalService globalService;

    @Autowired
    private ProductImageMapper productImageMapper;

    private final String folder = "eshop/products";

    @Override
    public ApiResponse<List<ProductImageResponse>> uploadProductImage(ProductImageRequest productImageRequest) {

        Product product = globalService.getProductById(productImageRequest.getProductId());

        List<MultipartFile> files = productImageRequest.getProductImageFiles();
        List<ProductImage> productImageList = new ArrayList<>();

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString().substring(0, 5);

            try {
                byte[] data = new byte[file.getInputStream().available()];
                file.getInputStream().read(data);

                String url = cloudinary.uploader().upload(data, ObjectUtils.asMap(
                        "public_id", fileName,
                        "folder", folder

                )).get("url").toString();

                ProductImage productImage = ProductImage.builder()
                        .url(url)
                        .publicId(folder + "/" + fileName)
                        .product(product)
                        .build();

                productImage = productImageRepository.save(productImage);

                productImageList.add(productImage);

            } catch (IOException e) {

                throw new RuntimeException("Lỗi nhập xuất file");
            }
        }

        List<ProductImageResponse> productImageResponseList = productImageMapper.mapToProductImageResponseList(productImageList);
        return new ApiResponse<>(1, productImageResponseList);
    }

    @Override
    public Message deleteProductImage(DeleteProductImageRequest deleteProductImageRequest) {

        try {

            Optional<ProductImage> productImageOptional = productImageRepository.findByPublicId(deleteProductImageRequest.getPublicId());
            if (productImageOptional.isEmpty()) {

                throw new ResourceNotFoundException("Không tìm thấy ảnh sản phẩm");
            }

            Map result = cloudinary.uploader().destroy(deleteProductImageRequest.getPublicId(), ObjectUtils.emptyMap());

            ProductImage productImage = productImageOptional.get();
            productImageRepository.delete(productImage);

            return new Message(1, result.get("result").toString());

        } catch (IOException e) {
            throw new RuntimeException("Lỗi nhập xuất");
        }
    }
}
