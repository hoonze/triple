package com.triple.review.common.errors;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String reviewId) {
        super("[" + reviewId + "] reviewId does not exist.");
    }

    public ReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
