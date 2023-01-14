package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.CategoryResponseDto;
import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
import joeuncamp.dabombackend.global.constant.CategoryGroup;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.error.exception.CCreationDeniedException;
import joeuncamp.dabombackend.global.error.exception.CIllegalArgumentException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
     * @param requestDto 강좌 개설 정보
     * @param memberId 개설을 요청한 회원 아이디넘버
     * @return 개설된 강좌의 아이디넘버
     */
    public long openCourse(CourseDto.CreationRequest requestDto, Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        if (!creatorService.hasCreatorProfile(member)) {
            throw new CCreationDeniedException();
        }
        CreatorProfile creator = member.getCreatorProfile();
        Course course = createAndSaveCourse(requestDto, creator);
        return course.getId();
    }

    private Course createAndSaveCourse(CourseDto.CreationRequest dto, CreatorProfile creator) {
        Course course = dto.toEntity(creator);
        courseJpaRepository.save(course);
        return course;
    }

    /**
     * 강좌의 정보를 조회합니다.
     * @param courseId 조회할 강좌 아이디넘버
     * @return 강좌 정보
     */
    public CourseDto.Response getCourse(Long courseId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        return new CourseDto.Response(course);
    }

    /**
     * 카테고리 내의 모든 강좌 정보를 조회합니다.
     * @param category 카테고리명
     * @return 강좌 정보 리스트
     */
    public List<CourseDto.ShortResponse> getCoursesByCategory(String category) {
        CategoryType type = CategoryType.findByTitle(category);
        if (type.equals(CategoryType.EMPTY)){
            throw new CIllegalArgumentException();
        }
        List<Course> courses = courseJpaRepository.findAllByCategory(type);
        return courses.stream()
                .map(CourseDto.ShortResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 모든 카테고리를 반환합니다.
     * @return
     */
    public List<CategoryResponseDto> getAllCategory(){
        List<CategoryResponseDto> responseDto = new ArrayList<>();
        return Arrays.stream(CategoryGroup.values()).map(CategoryResponseDto::new).collect(Collectors.toList());
    }
}
