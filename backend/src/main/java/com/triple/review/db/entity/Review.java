package com.triple.review.db.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

//        "type": "REVIEW",
//        "action": "ADD", /* "MOD", "DELETE" */
//        "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
//        "content": "좋아요!",
//        "attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
//        851d-4a50-bb07-9cc15cbdc332"],
//        "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
//        "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Review {
    @Id
    private String reviewId;

    private String content;

    private String userId;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> attachedPhotoIds;

    private String placeId;
}
