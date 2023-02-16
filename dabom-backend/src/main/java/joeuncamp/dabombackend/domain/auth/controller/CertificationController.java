package joeuncamp.dabombackend.domain.auth.controller;

import im.toss.cert.sdk.TossCertSession;
import im.toss.cert.sdk.TossCertSessionGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import joeuncamp.dabombackend.domain.auth.dto.CertRequestDto;
import joeuncamp.dabombackend.domain.auth.service.CertificationService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.Header;
import joeuncamp.dabombackend.util.tossapi.TossService;
import joeuncamp.dabombackend.util.tossapi.dto.AuthResultResponse;
import joeuncamp.dabombackend.util.tossapi.dto.MemberPrivacy;
import joeuncamp.dabombackend.util.tossapi.dto.TxIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[1-4.Certification]", description = "본인 인증과 관련된 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CertificationController {
    private final TossService tossService;
    private final CertificationService certificationService;

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
    public ResponseEntity<?> certify(@RequestBody CertRequestDto requestDto, @AuthenticationPrincipal Member member) {
        requestDto.setMemberId(member.getId());
        certificationService.certify(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
