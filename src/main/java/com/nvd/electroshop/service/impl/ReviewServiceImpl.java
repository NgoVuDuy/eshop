package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.ReviewRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.ReviewResponse;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.Review;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.exception.BadRequestException;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.ReviewMapper;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.repository.ReviewRepository;
import com.nvd.electroshop.service.GlobalService;
import com.nvd.electroshop.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final GlobalService globalService;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewMapper reviewMapper,
                             GlobalService globalService
                             ) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.globalService = globalService;
    }

    @Override
    // Lấy các đánh giá của một sản phẩm
    public ApiResponse<List<ReviewResponse>> getReviewsByProductId(Long productId) {

        List<Review> reviewList = reviewRepository.findByProduct_Id(productId);
        List<ReviewResponse> reviewResponseList = reviewMapper.mapToReviewResponseList(reviewList);

        return new ApiResponse<>(1, reviewResponseList);
    }

    @Override
    // Lấy các đánh giá của người dùng hiện tại
    public ApiResponse<List<ReviewResponse>> getAllUserReviews() {

        User user = globalService.getUserByToken();

        List<Review> reviewList = user.getReviews();
        List<ReviewResponse> reviewResponseList = reviewMapper.mapToReviewResponseList(reviewList);

        return new ApiResponse<>(1, reviewResponseList);
    }

    @Override
    // Lấy đánh giá của người dùng về một sản phẩm
    public ApiResponse<ReviewResponse> getUserReviewByProductId(Long productId) {

        Product product = globalService.getProductById(productId);
        User user = globalService.getUserByToken();
        Review review = this.getReviewOrThrow(user, product);

        ReviewResponse reviewResponse = reviewMapper.mapToReviewResponse(review);

        return new ApiResponse<>(1, reviewResponse);
    }

    @Override
    // Tạo mới một đánh giá cho một sản phẩm
    public ApiResponse<ReviewResponse> createUserReview(ReviewRequest reviewRequest) {

        Product product = globalService.getProductById(reviewRequest.getProductId());
        User user = globalService.getUserByToken();
        Review review = createReviewOrThrow(user, product, reviewRequest);

        review = reviewRepository.save(review);
        ReviewResponse reviewResponse = reviewMapper.mapToReviewResponse(review);

        return new ApiResponse<>(1, reviewResponse);
    }

    @Override
    // Cập nhật đánh giá
    public ApiResponse<ReviewResponse> updateUserReview(Long productId, ReviewRequest reviewRequest) {

        Product product = globalService.getProductById(productId);
        User user = globalService.getUserByToken();
        Review review = this.getReviewOrThrow(user, product);

        if (reviewRequest.getComment() != null) review.setComment(reviewRequest.getComment());
        if(reviewRequest.getRating() != 0) review.setRating(reviewRequest.getRating());

        review = reviewRepository.save(review);
        ReviewResponse reviewResponse = reviewMapper.mapToReviewResponse(review);

        return new ApiResponse<>(1, reviewResponse);
    }

    @Override
    // Xóa đánh giá
    public Message deleteUserReview(Long productId) {

        Product product = globalService.getProductById(productId);
        User user = globalService.getUserByToken();
        Review review = this.getReviewOrThrow(user, product);

        reviewRepository.delete(review);
        return new Message(1,"Xóa đánh giá thành công");
    }

    private Review getReviewOrThrow(User user, Product product) {

        if (!reviewRepository.existsByUserAndProduct(user, product)) {

            throw new ResourceNotFoundException("Bạn chưa đánh giá sản phẩm này rồi");
        }

        return reviewRepository.findByUserAndProduct(user, product);
    }

    private Review createReviewOrThrow(User user, Product product, ReviewRequest reviewRequest) {

        if (reviewRepository.existsByUserAndProduct(user, product)) {

            throw new BadRequestException("Bạn đã đánh giá sản phẩm này rồi");
        }

        return reviewMapper.mapToReview(user, product, reviewRequest);
    }
}