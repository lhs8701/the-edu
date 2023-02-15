package joeuncamp.dabombackend.domain.creator.service;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.creator.dto.CreatorDto;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorCourseService {
    private final MemberJpaRepository memberJpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    /**
     * 크리에이터가 업로드한 강좌 목록을 조회합니다.
     *
     * @param memberId 크리에이터
     * @return 업로드한 강좌 목록
     */
    public List<CourseDto.ShortResponse> getUploadedCourses(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        if (!member.isCreator()) {
            throw new CAccessDeniedException("크리에이터만 이용할 수 있는 기능입니다.");
        }
        return courseJpaRepository.findByCreatorProfile(member.getCreatorProfile()).stream()
                .map(CourseDto.ShortResponse::new)
                .toList();
    }
}
