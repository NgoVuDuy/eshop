package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.ReviewRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.ReviewResponse;
import com.nvd.electroshop.entity.Review;
import com.nvd.electroshop.service.ReviewService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/users/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllUserReviews() {

        return ResponseEntity.ok(reviewService.getAllUserReviews());
    }

    @GetMapping("/users/reviews/{productId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getUserReviewByProductId(@PathVariable Long productId) {

        return ResponseEntity.ok(reviewService.getUserReviewByProductId(productId));
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByProductId(@PathVariable Long productId) {

        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }

    @PostMapping("/users/reviews")
    public ResponseEntity<ApiResponse<ReviewResponse>> createUserReview(@RequestBody ReviewRequest reviewRequest) {

        return ResponseEntity.ok(reviewService.createUserReview(reviewRequest));
    }

    @PutMapping("/users/reviews/{productId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateUserReview(@PathVariable Long productId, @RequestBody ReviewRequest reviewRequest) {

        return ResponseEntity.ok(reviewService.updateUserReview(productId, reviewRequest));
    }
    @DeleteMapping("/users/reviews/{productId}")
    public ResponseEntity<Message> deleteUserReview(@PathVariable Long productId) {

        return ResponseEntity.ok(reviewService.deleteUserReview(productId));
    }
}
