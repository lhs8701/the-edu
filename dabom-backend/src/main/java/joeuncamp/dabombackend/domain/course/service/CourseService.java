package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.CourseCreationRequestDto;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    public long openCourse(CourseCreationRequestDto dto, Long memberId) {
        return 1L;
    }
}
