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
import com.nvd.electroshop.mapper.ProductImageMapper;
import com.nvd.electroshop.repository.ProductImageRepository;
import com.nvd.electroshop.service.GlobalService;
import com.nvd.electroshop.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final Cloudinary cloudinary;
    private final GlobalService globalService;
    private final ProductImageMapper productImageMapper;
    private final String folder = "eshop/products";

    public ProductImageServiceImpl(ProductImageRepository productImageRepository, Cloudinary cloudinary, GlobalService globalService, ProductImageMapper productImageMapper) {
        this.productImageRepository = productImageRepository;
        this.cloudinary = cloudinary;
        this.globalService = globalService;
        this.productImageMapper = productImageMapper;
    }

    @Override
    public void uploadProductImages(ProductImageRequest productImageRequest, SseEmitter sseEmitter) {

        Product product = globalService.getProductById(productImageRequest.getProductId()); // Lấy sản phẩm cần thêm hình

        List<MultipartFile> files = productImageRequest.getProductImageFiles(); // Lấy các file hình
//        List<ProductImage> productImageList = new ArrayList<>();

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString().substring(0, 5);

            try {
                byte[] data = new byte[file.getInputStream().available()];
                file.getInputStream().read(data);

                String url = cloudinary.uploader().upload(data, ObjectUtils.asMap(
                        "public_id", fileName,
                        "folder", folder

                )).get("url").toString(); // upload lên cloudinary

                ProductImage productImage = ProductImage.builder()
                        .url(url)
                        .publicId(folder + "/" + fileName)
                        .product(product)
                        .build();

                productImage = productImageRepository.save(productImage); // Lưu url vào db

//                productImageList.add(productImage);

                ProductImageResponse productImageResponse = productImageMapper.mapToProductImageResponse(productImage);

                //emit
                sseEmitter.send(SseEmitter.event().name("uploaded").data(
                        new ApiResponse<>(1, productImageResponse)
                ));

            } catch (IOException e) {

                throw new RuntimeException("Lỗi nhập xuất file");
            }
        }

        // Kết quả trả về
//        List<ProductImageResponse> productImageResponseList = productImageMapper.mapToProductImageResponseList(productImageList);
//        return new ApiResponse<>(1, productImageResponseList);
    }

    @Override
    @Transactional
    public Message deleteProductImage(DeleteProductImageRequest deleteProductImageRequest) {

        List<String> publicIds = deleteProductImageRequest.getPublicIds();

        for (String publicId : publicIds) {
            try {
                // XÓA DB trước
                productImageRepository.deleteByPublicId(publicId);

                // XÓA CLOUDINARY sau
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            } catch (IOException e) {
                throw new RuntimeException("Lỗi nhập xuất");
            }
        }

        return new Message(1, "Xóa ảnh sản phẩm thành công");
    }

    @Override
    @Transactional
    public Message deleteAllProductImages(Long productId) {

        Product product = globalService.getProductById(productId);

        List<ProductImage> productImageList = product.getProductImages();
        List<String> publicIds = productImageList.stream().map(ProductImage::getPublicId).toList();

        int n = productImageRepository.deleteByPublicIdIn(publicIds);
        productImageRepository.flush();

        for (String publicId : publicIds) {
            try {

                Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

                if(!"ok".equals(result.get("result").toString())) {

                    throw new RuntimeException("Không xóa được ảnh trên cloudinary");
                }

            } catch (IOException e) {
                throw new RuntimeException("Lỗi nhập xuất");
            }
        }

        if(n > 0) {
            return new Message(1, "Xóa ảnh sản phẩm thành công");

        }
        return new Message(1, "Xóa ảnh sản phẩm thất bại");

    }


}
