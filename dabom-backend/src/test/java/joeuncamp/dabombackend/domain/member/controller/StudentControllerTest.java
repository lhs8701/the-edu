package joeuncamp.dabombackend.domain.member.controller;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.dto.CourseStatusDto;
import joeuncamp.dabombackend.domain.course.service.MyCourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MyCourseService myCourseService;

    @Test
    @WithMockUser
    @DisplayName("찜한 강좌의 목록을 조회한다.")
    void 찜한_모든_강좌의_목록을_조회한다() throws Exception {
        // given
        CourseDto.ShortResponse dto = CourseDto.ShortResponse.builder()
                .courseId(1L)
                .build();
        List<CourseDto.ShortResponse> responseDto = List.of(dto);
        given(myCourseService.getWishedCourses(1L)).willReturn(responseDto);

        // when
        final ResultActions actions = mockMvc.perform(get("/api/students/{memberId}/courses/wish", "1"));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].courseId", equalTo(1)));
    }
}
