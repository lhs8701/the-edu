package joeuncamp.dabombackend.util.tossapi;

import joeuncamp.dabombackend.domain.order.dto.Data;
import joeuncamp.dabombackend.domain.order.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TossService {
    @Value("${api.toss.confirm}")
    private String CONFIRM_API;

    public PaymentInfo confirmPayment(Data data) {
        WebClient webClient = WebClient.create();
        String encodedAuth = new String(Base64.getEncoder().encode("test_sk_zXLkKEypNArWmo50nX3lmeaxYG5R:".getBytes(StandardCharsets.UTF_8)));
        PaymentInfo paymentInfo = webClient.method(HttpMethod.POST)
                .uri(CONFIRM_API)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", encodedAuth)
                .bodyValue(data)
                .retrieve()
                .bodyToMono(PaymentInfo.class)
                .block();
        return paymentInfo;
    }
}
