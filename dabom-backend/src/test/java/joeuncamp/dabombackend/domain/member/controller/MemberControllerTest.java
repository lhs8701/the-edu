package joeuncamp.dabombackend.domain.member.controller;

import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.member.MemberController;
import joeuncamp.dabombackend.domain.member.dto.ProfileResponseDto;
import joeuncamp.dabombackend.domain.member.dto.ProfileUpdateParam;
import joeuncamp.dabombackend.domain.member.service.MemberService;
import joeuncamp.dabombackend.global.WithAuthUser;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @WithMockUser
    @DisplayName("나의 프로필을 조회한다.")
    void 나의_프로필을_조회한다() throws Exception {
        // given
        ProfileResponseDto profileResponseDto = ProfileResponseDto.builder()
                .id(1L)
                .account(ExampleValue.Member.ACCOUNT)
                .build();
        given(memberService.getMyProfile(1L)).willReturn(profileResponseDto);

        // when
        final ResultActions actions = mockMvc.perform(get("/api/members/{memberId}/profile", "1"));

        // then
        actions.andExpect(status().isOk());
    }

    @Test
    @WithAuthUser(role = "USER")
    @DisplayName("나의 프로필을 수정한다.")
    void 나의_프로필을_수정한다() throws Exception {
        // given
        ProfileUpdateParam updateParam = ProfileUpdateParam.builder()
                .nickname("updated")
                .email("updated")
                .build();
        given(memberService.updateMyProfile(updateParam, 1L)).willReturn(1L);

        // when
        final ResultActions actions = mockMvc.perform(patch("/api/members/{memberId}/profile", "1").with(csrf())
                .content(new Gson().toJson(updateParam))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        actions.andExpect(status().isOk());
    }
}
