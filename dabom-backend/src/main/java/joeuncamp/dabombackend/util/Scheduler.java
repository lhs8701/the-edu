package joeuncamp.dabombackend.util;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.RankedCourse;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.RankingJpaRepository;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.util.email.Email;
import joeuncamp.dabombackend.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final CourseJpaRepository courseJpaRepository;
    private final RankingJpaRepository rankingJpaRepository;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24 * 7)
    private void renewWeeklyRanking() {
        rankingJpaRepository.deleteAll();
        Arrays.stream(CategoryType.values()).forEach(type -> {
            Page<Course> pages = courseJpaRepository.findByEnrolledCountFromWeek(type);
            List<RankedCourse> rankedCourses = pages.getContent().stream()
                    .map(RankedCourse::new)
                    .toList();
            log.info("{}",rankedCourses);
            rankingJpaRepository.saveAll(rankedCourses);
        });
        log.info("일주일 간격으로 랭킹 갱신 완료");
    }
}
