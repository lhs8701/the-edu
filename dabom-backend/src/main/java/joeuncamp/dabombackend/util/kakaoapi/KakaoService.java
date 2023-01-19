package joeuncamp.dabombackend.util.kakaoapi;

import joeuncamp.dabombackend.global.error.exception.CCommunicationFailedException;
import joeuncamp.dabombackend.util.kakaoapi.dto.KakaoProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoService {

    @Value("${api.kakao.profile}")
    private static String PROFILE_API;

    @Value("${api.kakao.logout}")
    private static String LOGOUT_API;

    public KakaoProfile getKakaoProfile(String kakaoToken) {
        WebClient webClient = WebClient.create();
        return webClient.method(HttpMethod.POST)
                .uri(PROFILE_API)
                .header("Authorization", "Bearer " + kakaoToken)
                .retrieve()
                .bodyToMono(KakaoProfile.class)
                .block();
    }

    public void logout(String kakaoToken) {
        WebClient webClient = WebClient.create();
        ResponseEntity<Void> responseEntity = webClient.post()
                .uri(LOGOUT_API)
                .header("Authorization", "Bearer " + kakaoToken)
                .retrieve()
                .toEntity(Void.class)
                .block();

        if (responseEntity == null || !responseEntity.getStatusCode().equals(HttpStatus.OK)){
            throw new CCommunicationFailedException();
        }
    }
}
