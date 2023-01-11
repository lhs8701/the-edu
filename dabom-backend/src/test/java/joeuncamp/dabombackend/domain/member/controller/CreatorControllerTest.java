package joeuncamp.dabombackend.domain.member.controller;

import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.controller.CreatorController;
import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
import joeuncamp.dabombackend.global.WithAuthUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
@WebMvcTest(CreatorController.class)
public class CreatorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CreatorService creatorService;

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("크리에이터 프로필을 활성화 한다.")
    void 크리에이터_프로필을_활성화_한다() throws Exception {
        // given
        CreatorRequestDto dto = CreatorRequestDto.builder().build();

        // when
        ResultActions actions = mockMvc.perform(post("/api/creators/activate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(dto)));

        // then
        actions.andExpect(status().isOk());
    }
}
