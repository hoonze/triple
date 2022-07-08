package com.triple.review.api.controller;


import com.triple.review.api.dto.PointResponse;
import com.triple.review.api.dto.ReviewRequest;
import com.triple.review.api.dto.ReviewResponse;
import com.triple.review.api.service.PointService;
import com.triple.review.api.service.ReviewService;
import com.triple.review.common.errors.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.triple.review.common.utils.ApiUtils.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final PointService pointService;

    @PostMapping("/events")
    public ApiResult<ReviewResponse> review(@RequestBody @Valid ReviewRequest reviewRequest, Errors errors){
        if(errors.hasErrors())
            throw new BadRequestException("Invalid Input");

        ReviewResponse reviewResponse = null;

        if("ADD".equals(reviewRequest.getAction())){
            reviewResponse = new ReviewResponse(reviewService.createReview(reviewRequest));
        }else if("MOD".equals(reviewRequest.getAction())){
            reviewResponse = new ReviewResponse(reviewService.updateReview(reviewRequest));
        }else if("DELETE".equals(reviewRequest.getAction())){
            reviewService.deleteReview(reviewRequest);
        }

        return success(reviewResponse);
    }

    @GetMapping("/points/{userId}")
    public ApiResult<PointResponse> getPoints(@PathVariable String userId){
        return success(pointService.selectPoint(userId));
    }
}
