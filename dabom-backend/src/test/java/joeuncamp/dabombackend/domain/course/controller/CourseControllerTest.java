package joeuncamp.dabombackend.domain.course.controller;

import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.course.dto.*;
import joeuncamp.dabombackend.domain.course.service.CourseService;
import joeuncamp.dabombackend.domain.course.service.CurriculumService;
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
    CurriculumService curriculumService;
    @MockBean
    RankingService rankingService;

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
}
