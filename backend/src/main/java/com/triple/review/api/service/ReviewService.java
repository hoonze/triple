package com.triple.review.api.service;

import com.triple.review.api.dto.ReviewRequest;
import com.triple.review.db.entity.Review;

public interface ReviewService {
    public Review createReview(ReviewRequest reviewRequest);

    public Review selectReview(String reviewId);

    public Review updateReview(ReviewRequest reviewRequest);

    public void deleteReview(ReviewRequest reviewRequest);

    public boolean isFirstReview(String reviewRequest);

    public boolean alreadyReviewed(String userId, String placeId);
}
