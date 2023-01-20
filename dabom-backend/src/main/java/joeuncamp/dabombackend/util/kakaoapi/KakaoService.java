package joeuncamp.dabombackend.util.kakaoapi;

import joeuncamp.dabombackend.global.error.exception.CCommunicationFailedException;
import joeuncamp.dabombackend.util.kakaoapi.dto.KakaoProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class KakaoService {

    @Value("${api.kakao.profile}")
    private String PROFILE_API;

    @Value("${api.kakao.logout}")
    private String LOGOUT_API;

    @Value("${api.kakao.unlink}")
    private String UNLINK_API;

    /**
     * 카카오 프로필 조회 API를 호출해 프로필 정보를 받아옵니다.
     *
     * @param kakaoToken 카카오에서 발급한 토큰
     * @return 카카오 프로필 정보
     */
    public KakaoProfile getKakaoProfile(String kakaoToken) {
        WebClient webClient = WebClient.create();
        return webClient.method(HttpMethod.POST)
                .uri(PROFILE_API)
                .header("Authorization", "Bearer " + kakaoToken)
                .retrieve()
                .bodyToMono(KakaoProfile.class)
                .block();
    }

    /**
     * 카카오 로그아웃 API를 호출하여 카카오 토큰을 만료시킵니다.
     *
     * @param kakaoToken 카카오에서 발급한 토큰
     */
    public void logout(String kakaoToken) {
        WebClient webClient = WebClient.create();
        ResponseEntity<Void> responseEntity = webClient.post()
                .uri(LOGOUT_API)
                .header("Authorization", "Bearer " + kakaoToken)
                .retrieve()
                .toEntity(Void.class)
                .block();

        if (responseEntity == null || !responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new CCommunicationFailedException();
        }
    }

    /**
     * 카카오 연결 종료 API를 호출해 카카오 계정과 연결을 끊습니다.
     *
     * @param kakaoToken 카카오에서 발급한 토큰
     */
    public void unlink(String kakaoToken) {
        WebClient webClient = WebClient.create();
        webClient.post()
                .uri(UNLINK_API)
                .header("Authorization", "Bearer " + kakaoToken)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
