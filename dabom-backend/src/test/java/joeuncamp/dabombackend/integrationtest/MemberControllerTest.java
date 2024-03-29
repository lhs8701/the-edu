package joeuncamp.dabombackend.integrationtest;


import com.google.gson.Gson;
import joeuncamp.dabombackend.domain.auth.dto.SignupRequestDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("회원을 생성한다.")
    void 회원을_생성한다() throws Exception {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .account(ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .build();

        // when
        String str = new Gson().toJson(signupRequestDto);
        final ResultActions actions = mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(str))
                .andDo(print());


        // then
        actions
                .andExpect(status().isOk());
    }
}
