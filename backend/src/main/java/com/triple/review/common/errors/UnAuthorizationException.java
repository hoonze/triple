package com.triple.review.common.errors;

public class UnAuthorizationException extends RuntimeException {
    public UnAuthorizationException(String message) {
        super(message);
    }

    public UnAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
