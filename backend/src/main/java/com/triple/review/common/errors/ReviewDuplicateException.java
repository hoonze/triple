package com.triple.review.common.errors;

public class ReviewDuplicateException extends RuntimeException {
    public ReviewDuplicateException(String userId, String placeId) {
        super("[" +userId+ "] user already written a review for the [" + placeId + "] place.");
    }

    public ReviewDuplicateException(String userId, String placeId, Throwable cause) {
        super("[" +userId+ "] user already written a review for the [" + placeId + "] place.", cause);
    }
}
