package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.global.common.PagingDto;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.util.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final Timer timer;
    private final CourseJpaRepository courseJpaRepository;

    public List<CourseDto.ShortResponse> findTop4FromCategory(String category){
        CategoryType type = CategoryType.findByTitle(category);
        Page<Course> pages = courseJpaRepository.findByEnrolledCountFromWeek(type);
        return pages.getContent().stream()
                .map(CourseDto.ShortResponse::new)
                .toList();
    }
}
