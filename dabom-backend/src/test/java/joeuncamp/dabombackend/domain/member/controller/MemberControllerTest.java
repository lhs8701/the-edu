package joeuncamp.dabombackend.domain.member.controller;


import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("회원을 조회한다.")
    void test() throws Exception {
        final ResultActions actions = mockMvc.perform(get("/members/35")
                        .header(Header.JWT_HEADER, ExampleValue.JWT.ACCESS));

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("account").value("user@naver.com"));
    }
}
