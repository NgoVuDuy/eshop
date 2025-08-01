package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.ReviewRequest;
import com.nvd.electroshop.dto.response.ReviewResponse;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.Review;
import com.nvd.electroshop.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewMapper {

    public ReviewResponse mapToReviewResponse(Review review) {

        return ReviewResponse.builder()
                .productId(review.getProduct().getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }

    public List<ReviewResponse> mapToReviewResponseList(List<Review> reviewList) {

        return reviewList.stream().map(this::mapToReviewResponse).toList();
    }

    public Review mapToReview(User user, Product product, ReviewRequest reviewRequest) {

        return Review.builder()
                .user(user)
                .product(product)
                .comment(reviewRequest.getComment())
                .rating(reviewRequest.getRating())
                .build();
    }
}
