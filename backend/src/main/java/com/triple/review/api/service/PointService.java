package com.triple.review.api.service;

import com.triple.review.api.dto.PointResponse;
import com.triple.review.api.dto.ReviewRequest;

public interface PointService {
    //포인트 적립
    public void earnPoints(ReviewRequest reviewRequest, int point);

    //회원별 포인트 조회
    public PointResponse selectPoint(String userId);
}
