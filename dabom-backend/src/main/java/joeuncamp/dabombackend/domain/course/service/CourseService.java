package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.CourseCreationRequestDto;
import joeuncamp.dabombackend.domain.course.dto.CourseResponseDto;
import joeuncamp.dabombackend.domain.course.dto.CourseShortResponseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.error.exception.CCreationDeniedException;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;

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
        Course course = createAndSaveCourse(dto, creatorProfile);
        return course.getId();
    }

    private Course createAndSaveCourse(CourseCreationRequestDto dto, CreatorProfile creatorProfile) {
        Course course = dto.toEntity(creatorProfile);
        courseJpaRepository.save(course);
        return course;
    }

    /**
     * 강좌의 정보를 조회합니다.
     * @param courseId 조회할 강좌 아이디넘버
     * @return 강좌 정보
     */
    public CourseResponseDto getCourse(Long courseId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        return new CourseResponseDto(course);
    }

    /**
     * 카테고리 내의 모든 강좌 정보를 조회합니다.
     * @param category 카테고리명
     * @return 강좌 정보 리스트
     */
    public List<CourseShortResponseDto> getCoursesByCategory(String category) {
        CategoryType type = CategoryType.findByTitle(category);
        if (type.equals(CategoryType.EMPTY)){
            throw new CIllegalArgumentException();
        }
        List<Course> courses = courseJpaRepository.findAllByCategory(type);
        return courses.stream()
                .map(CourseShortResponseDto::new)
                .collect(Collectors.toList());
    }
}
