package joeuncamp.dabombackend.domain.admin.service;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseAdminService {

    private final CourseJpaRepository courseJpaRepository;

    /**
     * 대기 상태의 강좌 목록을 조회합니다.
     *
     * @return 대기 상태의 강좌 목록
     */
    public List<CourseDto.ShortResponse> getInactiveCourses() {
        List<Course> courses = courseJpaRepository.findByActiveIsFalse();
        return courses.stream()
                .map(CourseDto.ShortResponse::new)
                .toList();
    }

}
