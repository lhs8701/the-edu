package joeuncamp.dabombackend.domain.creator.service;

import joeuncamp.dabombackend.domain.course.dto.CreatorStatusDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import joeuncamp.dabombackend.domain.player.record.service.ViewChecker;
import joeuncamp.dabombackend.domain.post.repository.ReviewJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreatorStatusService {
    private final OrderJpaRepository orderJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollJpaRepository enrollJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;
    private final ViewChecker viewChecker;

    /**
     * 크리에이터 수익 상태를 조회합니다.
     *
     * @param member 회원
     * @return 크리에이터 상태
     */
    public CreatorStatusDto getCreatorStatus(Member member) {
        Member found = memberJpaRepository.findById(member.getId()).orElseThrow(CResourceNotFoundException::new);
        if (!found.isCreator()) {
            throw new CAccessDeniedException("크리에이터만 이용할 수 있는 기능입니다.");
        }
        return getResponse(found.getCreatorProfile());
    }

    public CreatorStatusDto getResponse(CreatorProfile creator) {
        List<CreatorStatusDto.CourseStatus> courseStatusList = new ArrayList<>();
        List<Course> courses = creator.getUploadedCourses();
        for (Course course : courses) {
            Map<Integer, List<Long>> monthlyProfit = getMonthlyProfit(course);
            Long cancelCount = orderJpaRepository.countByCourseAndCanceled(course);
            Long studentCount = enrollJpaRepository.countByCourse(course);
            List<Member> members = enrollJpaRepository.findByCourse(course).stream()
                    .map(Enroll::getMember)
                    .toList();
            Long numOfCompleted = members.stream()
                    .filter(member -> viewChecker.watchedAll(member, course))
                    .count();
            Double averageScore = reviewJpaRepository.findScoreByCourse(course);
            courseStatusList.add(CreatorStatusDto.CourseStatus.builder()
                    .course(course)
                    .monthlyProfit(monthlyProfit)
                    .cancelCount(cancelCount)
                    .studentCount(studentCount)
                    .numOfCompleted(numOfCompleted)
                    .averageScore(averageScore)
                    .build());
        }
        Long totalProfit = orderJpaRepository.findProfitByCreator(creator);
        return new CreatorStatusDto(courseStatusList, totalProfit);
    }

    private Map<Integer, List<Long>> getMonthlyProfit(Course course) {
        LocalDate openedDate = course.getCreatedTime().toLocalDate();
        LocalDate startDate = YearMonth.from(openedDate).atDay(1);
        LocalDate endDate = YearMonth.from(openedDate).atEndOfMonth();
        Map<Integer, List<Long>> map = new HashMap<>();
        while (startDate.isBefore(LocalDate.now())) {
            if (!map.containsKey(startDate.getYear())) {
                map.put(startDate.getYear(), new ArrayList<>());
            }
            Long monthProfit = orderJpaRepository.findProfitByCourseInDuration(course, startDate, endDate);
            map.get(startDate.getYear()).add(monthProfit);
            startDate = startDate.plusMonths(1);
            endDate = YearMonth.from(startDate).atEndOfMonth();
        }
        return map;
    }
}
