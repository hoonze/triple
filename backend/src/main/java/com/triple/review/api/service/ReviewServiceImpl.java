package com.triple.review.api.service;

import com.triple.review.api.dto.ReviewRequest;
import com.triple.review.common.errors.ReviewDuplicateException;
import com.triple.review.common.errors.ReviewNotFoundException;
import com.triple.review.common.errors.UnAuthorizationException;
import com.triple.review.db.entity.Review;
import com.triple.review.db.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    private final PointService pointService;

    @Override
    @Transactional
    public Review createReview(ReviewRequest reviewRequest) {
        // 한 사용자는 장소마다 리뷰 1개만 작성 가능
        if (alreadyReviewed(reviewRequest.getUserId(), reviewRequest.getPlaceId()))
            throw new ReviewDuplicateException(reviewRequest.getUserId(), reviewRequest.getPlaceId());

        // 특정 장소에 첫 리뷰 작성인지 확인
        boolean isFirstReview = isFirstReview(reviewRequest.getPlaceId());

        Review review = requestToEntity(reviewRequest);

        reviewRepository.save(review);

        pointService.earnPoints(reviewRequest, calculatePoint(reviewRequest, isFirstReview));

        return review;
    }

    @Override
    public Review selectReview(String reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @Override
    @Transactional
    public Review updateReview(ReviewRequest reviewRequest) {
        Review oldReview = selectReview(reviewRequest.getReviewId());

        if(!reviewBelongToUser(oldReview, reviewRequest))
            throw new UnAuthorizationException("user does not have permission to review.");

        int point = calculatePoint(oldReview, reviewRequest);

        Review newReview = requestToEntity(reviewRequest);

        reviewRepository.save(newReview);

        //포인트 증감시 이력 생성
        if (point != 0)
            pointService.earnPoints(reviewRequest, point);

        return newReview;
    }

    @Override
    @Transactional
    public void deleteReview(ReviewRequest reviewRequest) {
        Review review = selectReview(reviewRequest.getReviewId());

        if(!reviewBelongToUser(review, reviewRequest))
            throw new UnAuthorizationException("user does not have permission to review.");

        reviewRepository.delete(review);

        pointService.earnPoints(reviewRequest, calculatePoint(reviewRequest, isFirstReview(reviewRequest.getPlaceId())));
    }

    @Override
    public boolean isFirstReview(String placeId) {
        return reviewRepository.existsByPlaceId(placeId);
    }

    @Override
    public boolean alreadyReviewed(String userId, String placeId) {
        return reviewRepository.existsByUserIdAndPlaceId(userId, placeId);
    }

    private boolean reviewBelongToUser(Review oldReview, ReviewRequest newReview){
        if(oldReview.getUserId().equals(newReview.getUserId()) && oldReview.getReviewId().equals(newReview.getReviewId()))
            return true;

        return false;
    }

    private Review requestToEntity(ReviewRequest reviewRequest) {
        return Review.builder()
                .reviewId(reviewRequest.getReviewId())
                .content(reviewRequest.getContent())
                .attachedPhotoIds(Arrays.asList(reviewRequest.getAttachedPhotoIds()))
                .placeId(reviewRequest.getPlaceId())
                .userId(reviewRequest.getUserId())
                .build();
    }

    // ADD, DELETE
    private int calculatePoint(ReviewRequest reviewRequest, boolean isFirstReview) {
        int value = 0;

        // 1자 이상 텍스트 작성
        if (!reviewRequest.getContent().isEmpty()) {
            value++;
        }
        // 1장 이상 사진 첨부
        if (reviewRequest.getAttachedPhotoIds().length > 0) {
            value++;
        }
        // 특정 장소 첫 리뷰 작성
        if (!isFirstReview) {
            value++;
        }

        if ("DELETE".equals(reviewRequest.getAction()))
            value *= -1;

        return value;
    }

    // MOD
    private int calculatePoint(Review oldReview, ReviewRequest newReview) {
        int value = 0;

        if (oldReview.getContent().isEmpty() && !newReview.getContent().isEmpty()) {
            value++;
        } else if (!oldReview.getContent().isEmpty() && newReview.getContent().isEmpty()) {
            value--;
        }

        if (oldReview.getAttachedPhotoIds().isEmpty() && newReview.getAttachedPhotoIds().length != 0) {
            value++;
        } else if (!oldReview.getAttachedPhotoIds().isEmpty() && newReview.getAttachedPhotoIds().length == 0) {
            value--;
        }

        return value;
    }
}