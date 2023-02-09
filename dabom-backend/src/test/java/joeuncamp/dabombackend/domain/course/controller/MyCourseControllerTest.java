package joeuncamp.dabombackend.domain.course.controller;

import com.google.gson.Gson;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        final ResultActions actions = mockMvc.perform(post("/api/courses/{courseId}/enroll", 1)
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
        ResultActions actions = mockMvc.perform(post("/api/courses/{courseId}/wish", 1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(requestDto)));

        // then
        actions.andExpect(status().isOk());
    }
}
