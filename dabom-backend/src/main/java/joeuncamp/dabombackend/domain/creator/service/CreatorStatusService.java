package joeuncamp.dabombackend.domain.creator.service;

import joeuncamp.dabombackend.domain.course.dto.CreatorStatusDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.order.repository.OrderJpaRepository;
import joeuncamp.dabombackend.domain.player.record.service.ViewChecker;
import joeuncamp.dabombackend.domain.post.repository.ReviewJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CAlreadyEnrolledCourse;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
            long profit = orderJpaRepository.findProfitByCourse(course);
            long cancelCount = orderJpaRepository.countByCourseAndCanceled(course);
            long studentCount = enrollJpaRepository.countByCourse(course);
            List<Member> members = enrollJpaRepository.findByCourse(course).stream()
                    .map(Enroll::getMember)
                    .toList();
            long numOfCompleted = members.stream()
                    .filter(member -> viewChecker.watchedAll(member, course))
                    .count();
            double averageScore = reviewJpaRepository.findScoreByCourse(course);
            courseStatusList.add(CreatorStatusDto.CourseStatus.builder()
                    .profit(profit)
                    .cancelCount(cancelCount)
                    .studentCount(studentCount)
                    .numOfCompleted(numOfCompleted)
                    .averageScore(averageScore)
                    .build());
        }
        long totalProfit = orderJpaRepository.findProfitByCreator(creator);
        return new CreatorStatusDto(courseStatusList, totalProfit);
    }
}
