package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.ProfileResponseDto;
import joeuncamp.dabombackend.domain.member.dto.ProfileUpdateParam;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberRepository;

    public ProfileResponseDto getMyProfile(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        return new ProfileResponseDto(member);
    }

    public Long updateMyProfile(ProfileUpdateParam profileUpdateParam, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        member.updateProfile(profileUpdateParam);
        return member.getId();
    }
}

