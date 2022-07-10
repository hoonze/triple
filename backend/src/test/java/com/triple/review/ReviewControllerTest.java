package com.triple.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.review.api.dto.ReviewRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    static ReviewRequest duplicateReview;

    static List<ReviewRequest> user1Requests, user2Requests, unauthorizedUser;

    @BeforeAll
    public void Setup() throws Exception {
        duplicateReview = ReviewRequest.builder()
                .type("Review")
                .action("ADD")
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667775")
                .content("중복 리뷰")
                .attachedPhotoIds(new String[]{})
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        unauthorizedUser = new ArrayList<>();

        unauthorizedUser.add(ReviewRequest.builder()
                .type("Review")
                .action("MOD")
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("권한이 없는 리뷰 수정 요청")
                .attachedPhotoIds(new String[]{"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"})
                .userId("3ede0ef2-92b7-4817-a5f3-0c12345678")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build()
        );

        unauthorizedUser.add(ReviewRequest.builder()
                .type("Review")
                .action("DELETE")
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요")
                .attachedPhotoIds(new String[]{"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"})
                .userId("3ede0ef2-92b7-4817-a5f3-0c12345678")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build()
        );

        user1Requests = new ArrayList<>();

        // user1 리뷰 등록
        user1Requests.add(ReviewRequest.builder()
                .type("Review")
                .action("ADD")
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요")
                .attachedPhotoIds(new String[]{"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"})
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build()
        );

        // user1 리뷰 수정
        user1Requests.add(ReviewRequest.builder()
                .type("Review")
                .action("MOD")
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("")
                .attachedPhotoIds(new String[]{"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"})
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build()
        );

        // // user1 리뷰 삭제
        user1Requests.add(ReviewRequest.builder()
                .type("Review")
                .action("DELETE")
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("")
                .attachedPhotoIds(new String[]{"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"})
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build()
        );

        user2Requests = new ArrayList<>();

        //user2 리뷰 등록
        user2Requests.add(ReviewRequest.builder()
                .type("Review")
                .action("ADD")
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667770")
                .content("좋아요")
                .attachedPhotoIds(new String[]{})
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f740")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build()
        );

        //user2 리뷰 수정
        user1Requests.add(ReviewRequest.builder()
                .type("Review")
                .action("MOD")
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667770")
                .content("")
                .attachedPhotoIds(new String[]{"afb0cef2-851d-4a50-bb07-9cc15cbdc333"})
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f740")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build()
        );

        //user2 리뷰 삭제
        user1Requests.add(ReviewRequest.builder()
                .type("Review")
                .action("MOD")
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667770")
                .content("수정된 리뷰")
                .attachedPhotoIds(new String[]{"afb0cef2-851d-4a50-bb07-9cc15cbdc333"})
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f740")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build()
        );
    }

    @BeforeEach
    public void beforeEach() throws Exception{
        mockMvc.perform(
                post("/events")
                        .content(objectMapper.writeValueAsString(user1Requests.get(0)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    @Nested
    class 리뷰작성{
        @Test
        public void 리뷰_작성_성공() throws Exception {
            ResultActions result = mockMvc.perform(
                    post("/events")
                            .content(objectMapper.writeValueAsString(user2Requests.get(0)))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            );

            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.response.reviewId", is(user2Requests.get(0).getReviewId())))
                    .andExpect(jsonPath("$.response.content", is(user2Requests.get(0).getContent())))
                    .andExpect(jsonPath("$.response.attachedPhotoIds").isArray())
                    .andExpect(jsonPath("$.response.userId", is(user2Requests.get(0).getUserId())))
                    .andExpect(jsonPath("$.response.placeId", is(user2Requests.get(0).getPlaceId())));
        }

        @Test
        @DisplayName("한 장소에 2개 이상의 리뷰를 등록할 경우")
        public void 리뷰_작성_실패() throws Exception {
            ResultActions result = mockMvc.perform(
                    post("/events")
                            .content(objectMapper.writeValueAsString(duplicateReview))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            );

            result.andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error.status", is(409)))
                    .andExpect(jsonPath("$.error.message", is ("[" + duplicateReview.getUserId() +  "] user already written a review for the [" + duplicateReview.getPlaceId() +"] place.")));
        }
    }

    @Nested
    class 리뷰_수정{
        @Test
        public void 리뷰_수정_성공() throws Exception {
            ResultActions result = mockMvc.perform(
                    post("/events")
                            .content(objectMapper.writeValueAsString(user1Requests.get(1)))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            );

            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.response.reviewId", is(user1Requests.get(1).getReviewId())))
                    .andExpect(jsonPath("$.response.content", is(user1Requests.get(1).getContent())))
                    .andExpect(jsonPath("$.response.attachedPhotoIds").isArray())
                    .andExpect(jsonPath("$.response.userId", is(user1Requests.get(1).getUserId())))
                    .andExpect(jsonPath("$.response.placeId", is(user1Requests.get(1).getPlaceId())));
        }

        @Test
        @DisplayName("본인이 작성한 리뷰가 아닌 리뷰 수정 요청")
        public void 리뷰_수정_실패() throws Exception {
            ResultActions result = mockMvc.perform(
                    post("/events")
                            .content(objectMapper.writeValueAsString(unauthorizedUser.get(0)))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            );

            result.andDo(print())
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error.status", is(401)))
                    .andExpect(jsonPath("$.error.message", is ("user does not have permission to review.")));
        }
    }

    @Nested
    class 리뷰_삭제{
        @Test
        public void 리뷰_삭제_성공() throws Exception {
            ResultActions result = mockMvc.perform(
                    post("/events")
                            .content(objectMapper.writeValueAsString(user1Requests.get(2)))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            );

            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)));
        }

        @Test
        @DisplayName("본인이 작성한 리뷰가 아닌 리뷰 삭제 요청")
        public void 리뷰_삭제_실패() throws Exception {
            ResultActions result = mockMvc.perform(
                    post("/events")
                            .content(objectMapper.writeValueAsString(unauthorizedUser.get(1)))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            );

            result.andDo(print())
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error.status", is(401)))
                    .andExpect(jsonPath("$.error.message", is ("user does not have permission to review.")));
        }
    }

    @Nested
    class 포인트_조회{
        @Test
        @DisplayName("텍스트 1자 이상 / 사진 1장 이상 / 특정 장소 첫 리뷰 작성 : 3점")
        public void 포인트_조회_성공1() throws Exception {
            String userId = user1Requests.get(0).getUserId();
            ResultActions result = mockMvc.perform(get("/points/" + userId));

            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.response.userId", is(userId)))
                    .andExpect(jsonPath("$.response.point", is(3)));
        }

        @Test
        @DisplayName("글 내용 삭제 : 3점 -> 2점")
        public void 포인트_조회_성공2() throws Exception {
            mockMvc.perform(
                    post("/events")
                            .content(objectMapper.writeValueAsString(user1Requests.get(1)))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            );

            String userId = user1Requests.get(1).getUserId();
            ResultActions result = mockMvc.perform(get("/points/" + userId));

            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.response.userId", is(userId)))
                    .andExpect(jsonPath("$.response.point", is(2)));
        }

        @Test
        @DisplayName("텍스트 1자 이상 : 1점")
        public void 포인트_조회_성공3() throws Exception {
            mockMvc.perform(
                    post("/events")
                            .content(objectMapper.writeValueAsString(user2Requests.get(0)))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            );

            String userId = user2Requests.get(0).getUserId();
            ResultActions result = mockMvc.perform(get("/points/" + userId));

            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.response.userId", is(userId)))
                    .andExpect(jsonPath("$.response.point", is(1)));
        }

        @Test
        @DisplayName("존재하지 않는 사용자 포인트 조회")
        public void 포인트_조회_실패() throws Exception {
            String userId = user2Requests.get(0).getUserId();
            ResultActions result = mockMvc.perform(get("/points/" + userId));

            result.andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error.status", is(404)))
                    .andExpect(jsonPath("$.error.message", is("[" + userId + "] userId does not exist.")));
        }
    }
}
