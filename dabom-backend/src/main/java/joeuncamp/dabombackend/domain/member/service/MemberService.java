package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.ProfileResponseDto;
import joeuncamp.dabombackend.domain.member.dto.ProfileUpdateParam;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.common.IdResponseDto;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberRepository;

    /**
     * 나의 개인 정보를 조회합니다.
     *
     * @param memberId 조회할 회원의 아이디넘버
     * @return 개인 정보
     */
    public ProfileResponseDto getMyProfile(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        return new ProfileResponseDto(member);
    }

    /**
     * 나의 개인 정보를 수정합니다. 수정 인자 중 null값을 제외하고 반영합니다.
     *
     * @param profileUpdateParam 수정 인자
     * @param memberId           수정할 회원의 아이디넘버
     * @return 수정한 회원의 아이디넘버
     */
    public IdResponseDto updateMyProfile(ProfileUpdateParam profileUpdateParam, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        member.updateProfile(profileUpdateParam);
        memberRepository.save(member);
        return new IdResponseDto(member.getId());
    }
}

