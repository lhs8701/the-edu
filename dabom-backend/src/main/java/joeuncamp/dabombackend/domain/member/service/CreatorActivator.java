package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.dto.CreatorDto;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatorActivator {

    private final CreatorProfileJpaRepository creatorProfileJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final CreatorService creatorService;

    /**
     * 회원을 크리에이터 대기 상태로 만듭니다.
     *
     * @param requestDto 회원
     */
    public void standByMember(CreatorDto.StandByRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (creatorService.hasCreatorProfile(member)) {
            throw new CBadRequestException("크리에이터 대기 신청은 한 번만 할 수 있습니다.");
        }
        CreatorProfile creatorProfile = requestDto.toEntity(member);
        creatorProfileJpaRepository.save(creatorProfile);
    }

    /**
     * 회원의 크리에이터 프로필을 활성화합니다.
     *
     * @param memberId 회원 아이디넘버
     */
    public void activateCreator(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        if (member.getCreatorProfile() == null || member.getCreatorProfile().isActivated()) {
            throw new CBadRequestException("이미 크리에이터 기능이 활성화된 계정입니다.");
        }
        CreatorProfile creatorProfile = member.getCreatorProfile();
        creatorProfile.activate();
        creatorProfileJpaRepository.save(creatorProfile);
    }
}
