package joeuncamp.dabombackend.domain.unit.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.record.service.ViewChecker;
import joeuncamp.dabombackend.domain.unit.dto.MyUnitDto;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyUnitService {
    private final ViewChecker viewChecker;
    private final MemberJpaRepository memberJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final EnrollService enrollService;

    /**
     * 나의 강의 수강 목록을 조회합니다.
     * 각 강의 마다 시청 완료 여부를 확인할 수 있습니다.
     * @param requestDto 회원, 조회할 강좌
     * @return 강의 목록
     */
    public List<MyUnitDto.Response> getUnitStatus(MyUnitDto.GetRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, course)){
            throw new CAccessDeniedException();
        }
        List<Unit> completedUnits = viewChecker.getCompletedUnit(member, course);
        return course.getUnitList().stream()
                .map(unit -> new MyUnitDto.Response(unit, completedUnits.contains(unit)))
                .toList();
    }
}
