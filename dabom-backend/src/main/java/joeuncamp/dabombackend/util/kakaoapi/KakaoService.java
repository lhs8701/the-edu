package joeuncamp.dabombackend.util.kakaoapi;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService {

    @Value("${auth.kakao.rest-key}")
    private String kakaoClientId;

    @Value("${auth.kakao.redirect-uri}")
    private String kakaoRedirectUri;


}
