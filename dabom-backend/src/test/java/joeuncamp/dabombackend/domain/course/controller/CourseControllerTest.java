package joeuncamp.dabombackend.domain.course.controller;

import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.controller.CourseController;
import joeuncamp.dabombackend.domain.course.dto.CourseCreationRequestDto;
import joeuncamp.dabombackend.domain.course.service.CourseService;
import joeuncamp.dabombackend.domain.member.service.MemberService;
import joeuncamp.dabombackend.global.WithAuthUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("강좌를 개설한다.")
    void 강좌를_개설한다() throws Exception {
        // given
        CourseCreationRequestDto dto = CourseCreationRequestDto.builder()
                .build();

        given(courseService.openCourse(dto, 1L)).willReturn(1L);

        // when
        final ResultActions actions = mockMvc.perform(post("/api/courses")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(dto)));

        // then
        actions.andExpect(status().isCreated());
    }
}
