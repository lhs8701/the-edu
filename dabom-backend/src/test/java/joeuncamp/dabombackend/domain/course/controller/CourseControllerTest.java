package joeuncamp.dabombackend.domain.course.controller;

import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.controller.CourseController;
import joeuncamp.dabombackend.domain.course.dto.CourseCreationRequestDto;
import joeuncamp.dabombackend.domain.course.dto.CourseResponseDto;
import joeuncamp.dabombackend.domain.course.dto.CourseThumbnailResponseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.service.CourseService;
import joeuncamp.dabombackend.domain.member.service.MemberService;
import joeuncamp.dabombackend.global.WithAuthUser;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.Disabled;
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

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("강좌를 단건 조회한다.")
    void 강좌를_단건_조회한다() throws Exception {
        //given
        Long courseId = 1L;
        CourseResponseDto responseDto = CourseResponseDto.builder()
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
        List<CourseThumbnailResponseDto> responseDto = List.of(CourseThumbnailResponseDto.builder()
                .title(ExampleValue.Course.TITLE)
                .build());
        given(courseService.getCoursesByCategory(category)).willReturn(responseDto);

        //when
        final ResultActions actions = mockMvc.perform(get("/api/courses/category/{category}", category)
                .with(csrf()));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", equalTo(ExampleValue.Course.TITLE)));
    }
}
