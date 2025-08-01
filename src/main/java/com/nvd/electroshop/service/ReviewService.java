package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.ReviewRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

//    System
    ApiResponse<List<ReviewResponse>> getReviewsByProductId(Long productId);
//    Users
    ApiResponse<List<ReviewResponse>> getAllUserReviews();
    ApiResponse<ReviewResponse> getUserReviewByProductId(Long productId);
    ApiResponse<ReviewResponse> createUserReview(ReviewRequest reviewRequest);
    ApiResponse<ReviewResponse> updateUserReview(Long productId, ReviewRequest reviewRequest);
    Message deleteUserReview(Long productId);

}
