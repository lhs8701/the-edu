package joeuncamp.dabombackend.domain.player.record.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.record.dto.ViewDto;
import joeuncamp.dabombackend.domain.player.record.entity.View;
import joeuncamp.dabombackend.domain.player.record.repository.ViewJpaRepository;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ViewChecker {
    private final ViewJpaRepository viewJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final EnrollJpaRepository enrollJpaRepository;
    private final EnrollService enrollService;

    /**
     * 해당 강의를 시청 완료 처리합니다.
     * 기존 완료 표시가 없던 강의에 한해서만 처리됩니다.
     *
     * @param requestDto 회원, 강의
     */
    public void completeUnit(ViewDto.CompleteRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, unit.getCourse())) {
            throw new CAccessDeniedException("등록하지 않은 강좌입니다.");
        }
        if (viewJpaRepository.findByMemberAndUnit(member, unit).isEmpty()) {
            View view = View.builder()
                    .member(member)
                    .unit(unit)
                    .build();
            viewJpaRepository.save(view);
        }
    }

    /**
     * 다 시청한 강좌 목록을 반환합니다.
     *
     * @param member 회원
     * @return 다 시청한 강좌 목록
     */
    public List<Course> getCompletedCourse(Member member) {
        List<Course> entireCourses = enrollJpaRepository.findAllByMember(member).stream()
                .map(Enroll::getCourse)
                .toList();
        List<Course> completedCourses = new ArrayList<>();
        for (Course course : entireCourses) {
            if (watchedAll(member, course)){
                completedCourses.add(course);
            }
        }
        return completedCourses;
    }

    public boolean watchedAll(Member member, Course course) {
        List<Unit> units = getCompletedUnit(member, course);
        return units.size() == course.getUnitList().size();
    }

    /**
     * 강좌 내에서 다 본 강의 목록을 조회합니다.
     *
     * @param member 회원
     * @param course 강좌
     * @return 다 본 강의 목록
     */
    public List<Unit> getCompletedUnit(Member member, Course course) {
        List<Unit> units = unitJpaRepository.findByCourse(course);
        return units.stream()
                .map(unit -> viewJpaRepository.findByMemberAndUnit(member, unit))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(View::getUnit)
                .toList();
    }


}
