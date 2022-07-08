package com.triple.review.common.errors;

import com.triple.review.common.utils.ApiUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.triple.review.common.utils.ApiUtils.*;

@ControllerAdvice
public class GeneralExceptionHandler {

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
        return newResponse(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(error(message, status), headers, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequestException(Exception e) {
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewDuplicateException.class)
    public ResponseEntity<?> DuplicateException(Exception e) {
        return newResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            ReviewNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<?> NotFoundException(Exception e) { return newResponse(e, HttpStatus.NOT_FOUND);}
}
