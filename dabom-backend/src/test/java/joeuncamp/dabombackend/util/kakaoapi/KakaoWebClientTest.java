package joeuncamp.dabombackend.util.kakaoapi;

import com.google.gson.Gson;
import joeuncamp.dabombackend.global.error.exception.CCommunicationFailedException;
import joeuncamp.dabombackend.util.kakaoapi.dto.KakaoProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

public class KakaoWebClientTest {

    @Test
    @DisplayName("카카오 프로필 API를 호출하면, 프로필 정보를 가져온다.")
    void 카카오_프로필_정보를_가져온다() {
        // given
        String kakaoToken = "1lsu9n3vzuLyRVeG5t5oTDcjYXbi9CekaK96kxXRCinJYAAAAYXHk73l";
        WebClient webClient = WebClient.create();

        // when
        final String PROFILE_API = "https://kapi.kakao.com/v2/user/me";
        ResponseEntity<KakaoProfile> responseEntity  = webClient.method(HttpMethod.POST)
                .uri(PROFILE_API)
                .header("Authorization", "Bearer " + kakaoToken)
                .retrieve()
                .toEntity(KakaoProfile.class)
                .block();

        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
    }

    @Test
    @DisplayName("카카오 로그아웃 API를 호출한다.")
    void 카카오_로그아웃한다() {
        // given
        String kakaoToken = "DOrgm4l0qD6vYFN7DbWLUWEY38FWpkZsSVoSZvUoCj10EQAAAYXHbEjx";
        WebClient webClient = WebClient.create();

        // when
        final String LOGOUT_API = "https://kapi.kakao.com/v1/user/logout";
        ResponseEntity<Void> responseEntity  = webClient.method(HttpMethod.POST)
                .uri(LOGOUT_API)
                .header("Authorization", "Bearer " + kakaoToken)
                .retrieve()
                .toEntity(Void.class)
                .block();

        // then
        if (responseEntity == null || !responseEntity.getStatusCode().equals(HttpStatus.OK)){
            throw new CCommunicationFailedException();
        }
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("카카오 계정 연결을 끊는다.")
    void 카카오_계정_연결을_끊는다() {
        // given
        String kakaoToken = "1lsu9n3vzuLyRVeG5t5oTDcjYXbi9CekaK96kxXRCinJYAAAAYXHk73l";
        WebClient webClient = WebClient.create();

        // when
        final String PROFILE_API = "https://kapi.kakao.com/v1/user/unlink";
        ResponseEntity<Void> responseEntity  = webClient.method(HttpMethod.POST)
                .uri(PROFILE_API)
                .header("Authorization", "Bearer " + kakaoToken)
                .retrieve()
                .toEntity(Void.class)
                .block();

        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
