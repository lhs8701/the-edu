package joeuncamp.dabombackend.domain.creator.service;

import joeuncamp.dabombackend.domain.creator.dto.CreatorDto;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.creator.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatorActivator {

    private final CreatorProfileJpaRepository creatorProfileJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final CreatorService creatorService;

    /**
     * 회원이 크리에이터 신청을 합니다.
     * 이후 해당 회원은 크리에이터 대기 상태가 됩니다.
     *
     * @param requestDto 회원
     */
    public void registerCreator(CreatorDto.StandByRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!member.isCertified()){
            throw new CMemberNotCertifiedException();
        }
        if (creatorService.hasCreatorProfile(member)) {
            throw new CBadRequestException("크리에이터 신청은 한 번만 할 수 있습니다.");
        }
        CreatorProfile creatorProfile = requestDto.toEntity(member);
        creatorProfileJpaRepository.save(creatorProfile);
    }
}
