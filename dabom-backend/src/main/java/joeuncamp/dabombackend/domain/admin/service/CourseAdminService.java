package joeuncamp.dabombackend.domain.admin.service;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CBadRequestException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import joeuncamp.dabombackend.util.email.Email;
import joeuncamp.dabombackend.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseAdminService {

    private final CourseJpaRepository courseJpaRepository;
    private final EmailService emailService;

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

    /**
     * 강좌를 활성화합니다.
     * 크리에이터에게 알림 메일을 보냅니다.
     *
     * @param courseId 활성화할 강좌
     */
    public void activateCourse(Long courseId) {
        Course course = courseJpaRepository.findById(courseId).orElseThrow(CResourceNotFoundException::new);
        if (course.isActive()) {
            throw new CBadRequestException("이미 활성화된 강좌입니다.");
        }
        course.activate();
        courseJpaRepository.save(course);
        emailService.sendMail(Email.courseAcceptEmail(course.getCreatorProfile().getMember().getEmail(), course.getTitle()));
    }
}
