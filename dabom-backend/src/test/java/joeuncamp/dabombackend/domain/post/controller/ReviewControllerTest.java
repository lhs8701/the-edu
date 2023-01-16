package joeuncamp.dabombackend.domain.post.controller;

import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.course.dto.ReviewDto;
import joeuncamp.dabombackend.domain.post.service.ReviewService;
import joeuncamp.dabombackend.global.WithAuthUser;
import joeuncamp.dabombackend.global.common.IdResponseDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {
    @Autowired
    MockMvc mockMvc;


    @MockBean
    ReviewService reviewService;

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("후기를 작성한다.")
    void 후기를_작성한다() throws Exception {
        // given
        ReviewDto.Request requestDto = ReviewDto.Request.builder()
                .memberId(1L)
                .courseId(1L)
                .content(ExampleValue.Post.CONTENT)
                .score(ExampleValue.Post.RATING)
                .build();

        given(reviewService.writeReview(requestDto)).willReturn(new IdResponseDto(1L));

        // when
        ResultActions actions = mockMvc.perform(post("/api/courses/reviews")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(requestDto)));


        // then
        actions
                .andExpect(status().isCreated());
    }

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("후기를 작성한다.")
    void 후기를_조회한다() throws Exception {
        // given
        given(reviewService.getReviews(1L)).willReturn(List.of(new ReviewDto.Response()));

        // when
        ResultActions actions = mockMvc.perform(get("/api/courses/{courseId}/reviews",1L)
                .with(csrf()));

        // then
        actions
                .andExpect(status().isOk());
    }
}
