package joeuncamp.dabombackend.domain.course.controller;

import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.dto.EnrollDto;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.course.service.MyCourseService;
import joeuncamp.dabombackend.domain.wish.dto.WishDto;
import joeuncamp.dabombackend.domain.wish.service.WishService;
import joeuncamp.dabombackend.global.WithAuthUser;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(MyCourseController.class)
public class MyCourseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    WishService wishService;
    @MockBean
    EnrollService enrollService;
    @MockBean
    MyCourseService myCourseService;

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
        ResultActions actions = mockMvc.perform(post("/api/wish/courses/{courseId}", 1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(requestDto)));

        // then
        actions.andExpect(status().isOk());
    }

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("찜한 강좌의 목록을 조회한다.")
    void 찜한_모든_강좌의_목록을_조회한다() throws Exception {
        // given
        CourseDto.ShortResponse dto = CourseDto.ShortResponse.builder()
                .courseId(1L)
                .build();
        List<CourseDto.ShortResponse> responseDto = List.of(dto);
        given(myCourseService.getWishedCourses(1L)).willReturn(responseDto);

        // when
        final ResultActions actions = mockMvc.perform(get("/api/wish/courses"));

        // then
        actions.andExpect(status().isOk());
    }
}
