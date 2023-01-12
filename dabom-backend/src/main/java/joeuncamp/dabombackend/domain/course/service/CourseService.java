package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.CourseCreationRequestDto;
import joeuncamp.dabombackend.domain.course.dto.CourseResponseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
import joeuncamp.dabombackend.global.error.exception.CCreationDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final MemberJpaRepository memberJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final CreatorService creatorService;

    /**
     * 강좌를 개설합니다. 크리에이터 프로필이 활성화되지 않은 경우, 예외가 발생합니다.
     *
     * @param dto      강좌 개설 정보
     * @param memberId 개설을 요청한 회원 아이디넘버
     * @return 개설된 강좌의 아이디넘버
     */
    public long openCourse(CourseCreationRequestDto dto, Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        if (!creatorService.hasCreatorProfile(member)) {
            throw new CCreationDeniedException();
        }
        CreatorProfile creatorProfile = member.getCreatorProfile();
        Course course = saveCourse(dto, creatorProfile);
        return course.getId();
    }

    private Course saveCourse(CourseCreationRequestDto dto, CreatorProfile creatorProfile) {
        Course course = dto.toEntity();
        course.setCreatorProfile(creatorProfile);
        courseJpaRepository.save(course);
        return course;
    }

    public CourseResponseDto getCourse(Long courseId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);

        return new CourseResponseDto(course);
    }

}
