package joeuncamp.dabombackend.util.tossapi;

import im.toss.cert.sdk.TossCertSession;
import im.toss.cert.sdk.TossCertSessionGenerator;
import joeuncamp.dabombackend.util.tossapi.dto.PaymentInfo;
import joeuncamp.dabombackend.util.tossapi.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class TossService {
    @Value("${api.toss.confirm}")
    private String CONFIRM_API;

    @Value("${api.toss.token}")
    private String TOKEN_API;

    @Value("${api.toss.txid}")
    private String TXID_API;

    @Value("${api.toss.auth-result}")
    private String AUTH_RESULT_API;
    @Value("${api.toss.cancel}")
    private String CANCEL_API;

    @Value("${toss.secret-key}")
    private String SECRET_KEY;
    @Value("${toss.client-secret}")
    private String CLIENT_SECRET;
    @Value("${toss.client-id}")
    private String CLIENT_ID;
    @Value("${toss.token}")
    private String TOKEN;

    /**
     * 결제 승인 API를 호출합니다.
     *
     * @param tossPayRequest request
     * @return response
     */
    public PaymentInfo confirmPayment(TossPayRequest tossPayRequest) {
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
     * 결제를 취소합니다.
     *
     * @param paymentKey 페이먼트 키
     */
    public void cancel(String paymentKey) {
        WebClient webClient = WebClient.create();
        String URL = CANCEL_API + "/" + paymentKey + "/cancel";
        String encodedAuth = Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes());
        webClient.method(HttpMethod.POST)
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + encodedAuth)
                .retrieve()
                .bodyToMono(Void.class)
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
     * @return txid
     */
    public TxIdResponse issueTxId() {
        WebClient webClient = WebClient.create();
        TxIdRequest request = new TxIdRequest();
        return webClient.method(HttpMethod.POST)
                .uri(TXID_API)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TxIdResponse.class)
                .block();
    }

    /**
     * 본인인증 결과를 조회합니다.
     *
     * @param txId txid
     * @return response
     */
    public AuthResultResponse getAuthResult(String txId, TossCertSession tossCertSession) {
        WebClient webClient = WebClient.create();

        return webClient.method(HttpMethod.POST)
                .uri(AUTH_RESULT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .bodyValue(new AuthResultRequest(txId, tossCertSession.getSessionKey()))
                .retrieve()
                .bodyToMono(AuthResultResponse.class)
                .block();
    }


    /**
     * 토스 본인인증 결과로 받은 개인정보를 복호화합니다.
     *
     * @param authResultResponse 토스 본인인증 결과  DTO
     * @param tossCertSession    토스 세션
     * @return 회원 개인정보
     */
    public MemberPrivacy decryptPersonalData(AuthResultResponse authResultResponse, TossCertSession tossCertSession) {
        AuthResultResponse.Success.PersonalData personalData = authResultResponse.getSuccess().getPersonalData();
        return MemberPrivacy.builder()
                .name(tossCertSession.decrypt(personalData.getName()))
                .mobile(tossCertSession.decrypt(personalData.getPhone()))
                .birthday(tossCertSession.decrypt(personalData.getBirthday()))
                .build();
    }
}

