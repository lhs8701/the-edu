package joeuncamp.dabombackend.domain.member.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.dto.CreatorRequestDto;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CAlreadyCreatorException;
import joeuncamp.dabombackend.global.error.exception.CCreationDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatorService {
    public boolean hasCreatorProfile(Member member) {
        return member.getCreatorProfile() != null;
    }


    /**
     * 해당 회원이 주어진 강좌의 주인인지 확인합니다.
     *
     * @param course 강좌
     * @param creatorProfile 크리에이터
     */
    public void identifyCourseOwner(Course course, CreatorProfile creatorProfile) {
        if (!course.getCreatorProfile().equals(creatorProfile) || !creatorProfile.isActivated()) {
            throw new CAccessDeniedException();
        }
    }
}
