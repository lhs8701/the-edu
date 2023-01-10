package joeuncamp.dabombackend.domain.member.controller;


import joeuncamp.dabombackend.domain.member.MemberController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberSecurityTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("")
    void test() throws Exception {
        final ResultActions actions = mockMvc.perform(get("/members/35"));

        actions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("커스텀 코드"));
    }

}
