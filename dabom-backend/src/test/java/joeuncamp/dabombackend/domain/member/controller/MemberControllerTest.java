package joeuncamp.dabombackend.domain.member.controller;

import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.member.dto.ProfileDto;
import joeuncamp.dabombackend.domain.member.service.MemberService;
import joeuncamp.dabombackend.domain.member.service.AccountManager;
import joeuncamp.dabombackend.global.WithAuthUser;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(MemberController.class)
public class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @MockBean
    AccountManager accountManager;

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("나의 프로필을 조회한다.")
    void 나의_프로필을_조회한다() throws Exception {
        // given
        ProfileDto.Response profileResponseDto = ProfileDto.Response.builder()
                .id(1L)
                .account(ExampleValue.Member.ACCOUNT)
                .build();
        given(memberService.getMyProfile(1L)).willReturn(profileResponseDto);

        // when
        final ResultActions actions = mockMvc.perform(get("/api/members/me/profile"));

        // then
        actions.andExpect(status().isOk());
    }
}
