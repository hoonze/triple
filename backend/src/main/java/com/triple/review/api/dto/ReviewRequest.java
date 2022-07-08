package com.triple.review.api.dto;

//        "type": "REVIEW",
//        "action": "ADD", /* "MOD", "DELETE" */
//        "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
//        "content": "좋아요!",
//        "attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
//        851d-4a50-bb07-9cc15cbdc332"],
//        "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
//        "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewRequest {
    @NotEmpty
    private String type;

    @NotEmpty
    private String action;

    @NotEmpty
    private String reviewId;

    private String content;

    private String[] attachedPhotoIds;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String placeId;
}
