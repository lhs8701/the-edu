package joeuncamp.dabombackend.util.kakaoapi;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class KakaoWebClientTest {
    private WebTestClient webTestClient;

    @Value("${auth.kakao.api.profile}")
    private static String PROFILE_API;

    @Value("${auth.kakao.api.logout}")
    private static String LOGOUT_API;

    @Test
    @DisplayName("카카오 프로필 API를 호출하면, 프로필 정보를 가져온다")
    void 카카오_프로필_정보를_가져온다() {
        // given
        String kakaoToken = "";
        // when

        // then
        webTestClient.method(HttpMethod.POST)
                .uri(PROFILE_API)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + kakaoToken)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }
}
