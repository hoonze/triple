package com.triple.review.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PointResponse {
    private String userId;

    private int point;

    public PointResponse(String userId, int point){
        this.userId = userId;
        this.point = point;
    }
}
