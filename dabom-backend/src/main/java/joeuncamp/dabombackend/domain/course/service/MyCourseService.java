package joeuncamp.dabombackend.domain.course.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.dto.NextUnitInfo;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.record.dto.RecordDto;
import joeuncamp.dabombackend.domain.player.record.service.RecordService;
import joeuncamp.dabombackend.domain.player.record.service.ViewChecker;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import joeuncamp.dabombackend.domain.wish.repository.WishJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CMemberExistException;
import joeuncamp.dabombackend.global.error.exception.CRefreshTokenExpiredException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MyCourseService {
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollJpaRepository enrollJpaRepository;
    private final ViewChecker viewChecker;
    private final WishJpaRepository wishJpaRepository;
    private final RecordService recordService;
    private final UnitJpaRepository unitJpaRepository;


    /**
     * 찜한 모든 강좌를 조회합니다.
     *
     * @param memberId 회원 아이디넘버
     * @return 찜한 강좌 목록
     */
    public List<CourseDto.ShortResponse> getWishedCourses(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        List<Wish> wishes = wishJpaRepository.findAllByMember(member);
        List<Course> wishedCourses = wishes.stream().map(Wish::getCourse).toList();
        return wishedCourses.stream().map(CourseDto.ShortResponse::new).toList();
    }

    /**
     * 시청 완료한 강좌 목록을 조회합니다.
     *
     * @param memberId 회원
     * @return 시청 완료한 강좌 목록
     */
    public List<CourseDto.StatusResponse> getCompletedCourses(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CRefreshTokenExpiredException::new);
        List<Course> completedCourses = viewChecker.getCompletedCourse(member);
        return mapToStatusResponse(completedCourses, member);
    }

    /**
     * 시청 중인 강좌 목록을 조회합니다.
     *
     * @param memberId 회원
     * @return 시청 중인 강좌 목록
     */
    public List<CourseDto.StatusResponse> getOngoingCourses(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CRefreshTokenExpiredException::new);
        List<Course> completedCourses = viewChecker.getCompletedCourse(member);
        List<Course> ongoingCourses = enrollJpaRepository.findAllByMember(member).stream()
                .map(Enroll::getCourse)
                .filter(Predicate.not(completedCourses::contains))
                .toList();
        return mapToStatusResponse(ongoingCourses, member);
    }

    /**
     * 최근에 시청한 강좌 3개를 조회합니다.
     *
     * @param memberId
     * @return
     */
    public List<CourseDto.StatusResponse> getRecentPlayedCourses(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CMemberExistException::new);
        List<Course> recentCourses = recordService.findThreeRecentCourses(member);
        if (recentCourses == null) {
            return null;
        }
        return mapToStatusResponse(recentCourses, member);
    }

    private List<CourseDto.StatusResponse> mapToStatusResponse(List<Course> courses, Member member) {
        return courses.stream()
                .map(course -> new CourseDto.StatusResponse(course, viewChecker.getCompletedUnit(member, course).size(), makeNextUnitInfo(member, course)))
                .toList();
    }

    private NextUnitInfo makeNextUnitInfo(Member member, Course course) {
        RecordDto.Response recordResponse = recordService.getRecentPlayedUnit(member, course);
        Unit unit = unitJpaRepository.findById(recordResponse.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        return new NextUnitInfo(unit, recordResponse.getTime());
    }
}
