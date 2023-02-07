package joeuncamp.dabombackend.domain.auth.service;

import joeuncamp.dabombackend.domain.auth.dto.AppleAuthDto;
import joeuncamp.dabombackend.domain.auth.dto.SignupRequestDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleAuthService {
    private final MemberJpaRepository memberJpaRepository;

    public void signup(AppleAuthDto.SignupRequest requestDto) {
        if (memberJpaRepository.findByLoginTypeAndSocialId(LoginType.APPLE, requestDto.getSocialToken()).isPresent()) {
            throw new CMemberExistException();
        }
        createAndSaveMember(requestDto);
    }
    private void createAndSaveMember(AppleAuthDto.SignupRequest requestDto) {
        Member member = requestDto.toEntity();
        memberJpaRepository.save(member);
    }
}
