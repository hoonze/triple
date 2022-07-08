package com.triple.review.api.dto;

import com.triple.review.db.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponse {
    private String reviewId;
    private String content;
    private List<String> attachedPhotoIds;
    private String userId;
    private String placeId;

    public ReviewResponse(Review review){
        this.reviewId = review.getReviewId();
        this.content = review.getContent();
        this.userId = review.getUserId();
        this.placeId = review.getPlaceId();
        this.attachedPhotoIds = review.getAttachedPhotoIds();
    }
}
