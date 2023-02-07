package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.ProfileDto;
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
    public ProfileDto.Response getMyProfile(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        return new ProfileDto.Response(member);
    }

    /**
     * 나의 개인 정보를 수정합니다. 수정 인자 중 null값을 제외하고 반영합니다.
     *
     * @param requestDto 수정 인자, 회원
     */
    public void updateMyProfile(ProfileDto.UpdateRequest requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        member.updateProfile(requestDto.getNickname(), requestDto.getEmail(), requestDto.getProfileImage());
        memberRepository.save(member);
    }
}

