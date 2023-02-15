package joeuncamp.dabombackend.util.tossapi;

import im.toss.cert.sdk.TossCertSession;
import im.toss.cert.sdk.TossCertSessionGenerator;
import joeuncamp.dabombackend.domain.order.dto.OrderDto;
import joeuncamp.dabombackend.util.tossapi.dto.TossPayRequest;
import joeuncamp.dabombackend.util.tossapi.dto.PaymentInfo;
import joeuncamp.dabombackend.util.tossapi.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TossService {
    @Value("${api.toss.confirm}")
    private String CONFIRM_API;

    @Value("${api.toss.token}")
    private String TOKEN_API;

    @Value("${api.toss.txid}")
    private String TXID_API;

    @Value("${api.toss.auth-status}")
    private String AUTH_STATUS_API;

    @Value("${api.toss.auth-result}")
    private String AUTH_RESULT_API;

    @Value("${toss.secret-key}")
    private String SECRET_KEY;
    @Value("${toss.client-secret}")
    private String CLIENT_SECRET;
    @Value("${toss.client-id}")
    private String CLIENT_ID;

    private final TossCertSessionGenerator tossCertSessionGenerator;

    /**
     * 결제 승인 API를 호출합니다.
     *
     * @param tossPayRequest request
     * @return response
     */
    public PaymentInfo confirmPayment(OrderDto.Request.TossPayRequest tossPayRequest) {
        WebClient webClient = WebClient.create();
        String encodedAuth = Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes());
        return webClient.method(HttpMethod.POST)
                .uri(CONFIRM_API)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + encodedAuth)
                .bodyValue(tossPayRequest)
                .retrieve()
                .bodyToMono(PaymentInfo.class)
                .block();
    }

    /**
     * 본인인증을 위한 어세스토큰을 발급합니다.
     *
     * @return response
     */
    public String issueToken() {
        WebClient webClient = WebClient.create();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", CLIENT_ID);
        formData.add("client_secret", CLIENT_SECRET);
        formData.add("scope", "ca");
        return Objects.requireNonNull(webClient.method(HttpMethod.POST)
                .uri(TOKEN_API)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block()).getAccess_token();
    }

    /**
     * txid를 발급합니다.
     *
     * @param accessToken 토스 어세스토큰
     * @return txid
     */
    public TxIdResponse issueTxId(String accessToken) {
        WebClient webClient = WebClient.create();
        return webClient.method(HttpMethod.POST)
                .uri(TXID_API)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .bodyValue(new TxIdRequest("USER_NONE"))
                .retrieve()
                .bodyToMono(TxIdResponse.class)
                .block();
    }

    /**
     * 본인인증 진행 상태를 조회합니다.
     *
     * @param accessToken 토스 어세스토큰
     * @param txId        txid
     * @return response
     */
    public AuthStatusResponse getAuthStatus(String accessToken, String txId) {
        WebClient webClient = WebClient.create();
        return webClient.method(HttpMethod.POST)
                .uri(AUTH_STATUS_API)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .bodyValue(new AuthStatusRequest(txId))
                .retrieve()
                .bodyToMono(AuthStatusResponse.class)
                .block();
    }

    /**
     * 본인인증 결과를 조회합니다.
     *
     * @param accessToken 어세스토큰
     * @param txId        txid
     * @return response
     */
    public AuthResultResponse getAuthResult(String accessToken, String txId) {
        WebClient webClient = WebClient.create();

        return webClient.method(HttpMethod.POST)
                .uri(AUTH_RESULT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .bodyValue(new AuthResultRequest(txId, getSessionKey()))
                .retrieve()
                .bodyToMono(AuthResultResponse.class)
                .block();
    }

    /**
     * 세션 키를 발급합니다.
     *
     * @return 세션 키
     */
    public String getSessionKey() {
        TossCertSession tossCertSession = tossCertSessionGenerator.generate();
        return tossCertSession.getSessionKey();
    }
}

