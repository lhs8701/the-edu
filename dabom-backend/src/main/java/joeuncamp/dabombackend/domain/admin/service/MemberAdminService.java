package joeuncamp.dabombackend.domain.admin.service;

import joeuncamp.dabombackend.domain.admin.dto.MemberAdminDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CMemberNotFoundException;
import joeuncamp.dabombackend.util.tossapi.TossService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAdminService {
    private final MemberJpaRepository memberJpaRepository;
    private final TossService tossService;

    public List<MemberAdminDto.ShortResponse> getAllMember() {
        List<Member> members = memberJpaRepository.findAll();
        return members.stream()
                .map(MemberAdminDto.ShortResponse::new)
                .toList();
    }

    /**
     * 토스 본인인증을 위한 토큰을 발급합니다.
     * 만료기간은 1년입니다.
     *
     * @return 토스 토큰
     */
    public String issueTossToken() {
        return tossService.issueToken();
    }

    /**
     * 회원의 정지를 해제합니다.
     *
     * @param memberId 정지 해제할 회원
     */
    public void unlockMember(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CMemberNotFoundException::new);
        if (member.isAccountNonLocked()) {
            throw new CBadRequestException("정지된 회원만 해제할 수 있습니다.");
        }
        member.unlock();
        memberJpaRepository.save(member);
    }

    /**
     * 회원을 정지합니다.
     *
     * @param memberId 정지할 회원
     */
    public void lockMember(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CMemberNotFoundException::new);
        if (!member.isAccountNonLocked()) {
            throw new CBadRequestException("이미 정지된 회원입니다.");
        }
        member.lock();
        memberJpaRepository.save(member);
    }


    /**
     * 정지된 회원 목록 조회
     *
     * @return 정지된 회원 목록
     */
    public List<MemberAdminDto.ShortResponse> getLockedMembers() {
        List<Member> members = memberJpaRepository.findByLockedIsTrue();
        return members.stream()
                .map(MemberAdminDto.ShortResponse::new)
                .toList();
    }
}
