package joeuncamp.dabombackend.domain.course.controller;

import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.course.dto.*;
import joeuncamp.dabombackend.domain.course.service.CourseService;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.course.service.RankingService;
import joeuncamp.dabombackend.domain.wish.dto.WishDto;
import joeuncamp.dabombackend.domain.wish.service.WishService;
import joeuncamp.dabombackend.global.WithAuthUser;
import joeuncamp.dabombackend.global.common.IdResponseDto;
import joeuncamp.dabombackend.global.common.PagingDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    @MockBean
    EnrollService enrollService;

    @MockBean
    WishService wishService;

    @MockBean
    RankingService rankingService;

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("강좌를 개설한다.")
    void 강좌를_개설한다() throws Exception {
        // given
        CourseDto.CreationRequest requestDto = CourseDto.CreationRequest.builder()
                .build();

        given(courseService.openCourse(requestDto, 1L)).willReturn(new IdResponseDto(1L));

        // when
        final ResultActions actions = mockMvc.perform(post("/api/courses")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(requestDto)));

        // then
        actions.andExpect(status().isCreated());
    }

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("강좌를 단건 조회한다.")
    void 강좌를_단건_조회한다() throws Exception {
        //given
        Long courseId = 1L;
        CourseDto.Response responseDto = CourseDto.Response.builder()
                .title(ExampleValue.Course.TITLE)
                .build();
        given(courseService.getCourse(courseId)).willReturn(responseDto);

        //when
        final ResultActions actions = mockMvc.perform(get("/api/courses/{courseId}", courseId)
                .with(csrf()));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("title").value(ExampleValue.Course.TITLE));
    }

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("카테고리 내의 전체 강좌를 조회한다.")
    void 전체_강좌를_조회한다() throws Exception {
        //given
        String category = ExampleValue.Course.CATEGORY;
        Pageable pageable = PageRequest.of(0, 2);
        PagingDto<CourseDto.ShortResponse> responseDto = new PagingDto<>(0, 2, List.of(CourseDto.ShortResponse.builder().build()));
        given(courseService.getCoursesByCategory(category, pageable)).willReturn(responseDto);

        //when
        final ResultActions actions = mockMvc.perform(get("/api/courses/category/{category}", category)
                .with(csrf()));

        //then
        actions.andExpect(status().isOk());
    }

    @Test
    @WithAuthUser(role = "USER")
    void 강좌에_수강신청한다() throws Exception {
        //given

        Long memberId = 1L;
        Long courseId = 1L;
        EnrollDto.Request requestDto = EnrollDto.Request.builder()
                .memberId(memberId)
                .courseId(courseId)
                .build();

        //when
        final ResultActions actions = mockMvc.perform(post("/api/courses/enroll")
                .content(new Gson().toJson(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf()));

        //then
        actions.andExpect(status().isOk());
    }

    @WithAuthUser(role = "USER")
    @Test
    @DisplayName("강좌에 찜을 하거나, 해제한다.")
    void 강좌에_찜을_하거나_해제한다() throws Exception {
        // given
        WishDto.Request requestDto = WishDto.Request.builder()
                .memberId(1L)
                .courseId(1L)
                .build();

        // when
        ResultActions actions = mockMvc.perform(post("/api/courses/wish")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(requestDto)));

        // then
        actions.andExpect(status().isOk());
    }
}
