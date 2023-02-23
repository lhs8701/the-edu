package joeuncamp.dabombackend.domain.auth.service;

import im.toss.cert.sdk.TossCertSession;
import im.toss.cert.sdk.TossCertSessionGenerator;
import joeuncamp.dabombackend.domain.auth.dto.CertRequestDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import joeuncamp.dabombackend.util.tossapi.TossService;
import joeuncamp.dabombackend.util.tossapi.dto.AuthResultResponse;
import joeuncamp.dabombackend.util.tossapi.dto.MemberPrivacy;
import joeuncamp.dabombackend.util.tossapi.dto.TxIdRequest;
import joeuncamp.dabombackend.util.tossapi.dto.TxIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final TossService tossService;
    private final TossCertSessionGenerator tossCertSessionGenerator;
    private final MemberJpaRepository memberJpaRepository;

    /**
     * 프론트엔드에서 본인인증 표준창을 호출하기 위해 필요한 txid를 반환합니다.
     *
     * @return response
     */
    public TxIdResponse getTxId() {
        return tossService.issueTxId();
    }

    /**
     * 본인인증을 완료한 후, DB에 개인정보를 입력합니다.
     *
     * @param requestDto 회원, txid
     */
    public void certify(CertRequestDto requestDto) {
        TossCertSession tossCertSession = tossCertSessionGenerator.generate();
        AuthResultResponse response = tossService.getAuthResult(requestDto.getTxid(), tossCertSession);
        MemberPrivacy memberPrivacy = tossService.decryptPersonalData(response, tossCertSession);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CMemberNotFoundException::new);
        member.setCertified(memberPrivacy);
        memberJpaRepository.save(member);
    }
}
