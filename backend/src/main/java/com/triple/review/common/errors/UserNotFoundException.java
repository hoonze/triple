package com.triple.review.common.errors;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("[" + userId + "] userId does not exist.");
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
