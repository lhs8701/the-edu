package joeuncamp.dabombackend.domain.creator.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            throw new CAccessDeniedException("강좌의 소유자가 아닙니다.");
        }
    }
}
