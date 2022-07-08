package com.triple.review.api.service;

import com.triple.review.api.dto.PointResponse;
import com.triple.review.api.dto.ReviewRequest;
import com.triple.review.common.errors.UserNotFoundException;
import com.triple.review.db.entity.Log;
import com.triple.review.db.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final LogRepository logRepository;

    @Override
    public void earnPoints(ReviewRequest reviewRequest, int point) {
        logRepository.save(
                Log.builder()
                        .userId(reviewRequest.getUserId())
                        .reviewId(reviewRequest.getReviewId())
                        .value((long) point)
                        .remarks("")
                        .build()
        );
    }

    @Override
    public PointResponse selectPoint(String userId) {
        return new PointResponse(userId, logRepository.selectPointByUser(userId).orElseThrow(() -> new UserNotFoundException(userId)));
    }
}
