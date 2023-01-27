package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAlreadyCreatorException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreatorService {
    private final MemberJpaRepository memberJpaRepository;
    private final CreatorProfileJpaRepository creatorProfileJpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    /**
     * 회원의 크리에이터 프로필을 활성화합니다.
     * @param requestDto 회원 아이디넘버
     */
    public void activateCreatorProfile(CreatorRequestDto requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (hasCreatorProfile(member)){
            throw new CAlreadyCreatorException();
        }
        saveCreatorProfile(requestDto, member);
    }

    public boolean hasCreatorProfile(Member member) {
        return member.getCreatorProfile() != null;
    }

    private void saveCreatorProfile(CreatorRequestDto dto, Member member) {
        CreatorProfile creatorProfile = dto.toEntity(member);
        creatorProfileJpaRepository.save(creatorProfile);
    }
}
