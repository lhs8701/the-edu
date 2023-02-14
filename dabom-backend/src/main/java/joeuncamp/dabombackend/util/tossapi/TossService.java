package joeuncamp.dabombackend.util.tossapi;

import joeuncamp.dabombackend.domain.order.dto.Data;
import joeuncamp.dabombackend.domain.order.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TossService {
    @Value("${api.toss.confirm}")
    private String CONFIRM_API;

    @Value("${api.toss.token}")
    private String TOKEN_API;

    @Value("${toss.secret-key}")
    private String SECRET_KEY;
    @Value("${toss.client-secret}")
    private String CLIENT_SECRET;
    @Value("${toss.client-id}")
    private String CLIENT_ID;

    public PaymentInfo confirmPayment(Data data) {
        WebClient webClient = WebClient.create();
        String encodedAuth = Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes());
        return webClient.method(HttpMethod.POST)
                .uri(CONFIRM_API)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + encodedAuth)
                .bodyValue(data)
                .retrieve()
                .bodyToMono(PaymentInfo.class)
                .block();
    }

    public PaymentInfo issueToken() {
        WebClient webClient = WebClient.create();
        String encodedAuth = Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes());
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", "CLIENT_ID");
        formData.add("client_secret", "CLIENT_SECRET");
        formData.add("grant_type", "client_credentials");
        return webClient.method(HttpMethod.POST)
                .uri(TOKEN_API +
                        "grant_type=client_credentials&" +
                        "client_id=" + CLIENT_ID + "&" +
                        "client_secret=" + CLIENT_SECRET + "&" +
                        "scope=ca")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Basic " + encodedAuth)
                .retrieve()
                .bodyToMono(PaymentInfo.class)
                .block();
    }
}

