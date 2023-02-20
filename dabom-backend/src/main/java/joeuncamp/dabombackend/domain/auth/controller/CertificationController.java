package joeuncamp.dabombackend.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.auth.dto.CertRequestDto;
import joeuncamp.dabombackend.domain.auth.service.CertificationService;
import joeuncamp.dabombackend.domain.auth.service.EmailCertificationService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import joeuncamp.dabombackend.util.tossapi.dto.TxIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;

import java.net.URI;

@Tag(name = "[1-4.Certification]", description = "본인 인증과 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CertificationController {
    private final CertificationService certificationService;
    private final EmailCertificationService emailCertificationService;

    @Operation(summary = "이메일 인증", description = "이메일 인증을 시도합니다. 성공한 경우, 홈페이지로 리다이렉트됩니다.")
    @PreAuthorize("permitAll()")
    @GetMapping("/cert/email")
    public RedirectView certifyEmail(@RequestParam(name = "email") String email, @RequestParam(name = "auth-key") String authKey) {
        emailCertificationService.certifyEmail(email, authKey);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create("http://the-edu.co.kr/"));
//        headers.setLocation(URI.create("the-edu://"));
//        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        RedirectView redirectView = new RedirectView("the-edu://");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return redirectView;
    }

    @Operation(summary = "토스 txId 발급", description = "")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cert/txid")
    public ResponseEntity<TxIdResponse> getTxId() {
        TxIdResponse response = certificationService.getTxId();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "토스 본인인증", description = "본인인증을 완료한 후, DB에 개인정보를 저장합니다.")
    @Parameter(name = Header.ACCESS_TOKEN, description = "어세스토큰", required = true, in = ParameterIn.HEADER, example = ExampleValue.JWT.ACCESS)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cert/result")
    public ResponseEntity<Void> certify(@RequestBody CertRequestDto requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        certificationService.certify(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
