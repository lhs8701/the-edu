package joeuncamp.dabombackend.util.kakaoapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoService {

    @Value("${auth.kakao.rest-key}")
    private static String REST_API_KEY;

    @Value("${auth.kakao.redirect-uri}")
    private static String REDIRECT_URI;

    @Value("${auth.kakao.api.profile}")
    private static String PROFILE_API;

    public KakaoProfile getKakaoProfile(String kakaoToken) {
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(PROFILE_API)
                .header("Authorization", "Bearer " + kakaoToken)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToMono(KakaoProfile.class)
                .block();
    }
}
