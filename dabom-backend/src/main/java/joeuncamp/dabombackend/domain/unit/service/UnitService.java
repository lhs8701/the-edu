package joeuncamp.dabombackend.domain.unit.service;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.common.SingleResponseDto;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitJpaRepository unitJpaRepository;
    private final CreatorService creatorService;
    private final CourseJpaRepository courseJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollService enrollService;

    /**
     * 강의를 업로드합니다.
     *
     * @param unitDto 강의 업로드 DTO
     * @return 생성된 강의 아이디
     */
    public SingleResponseDto<Long> uploadUnit(UnitDto unitDto) {
        UnitDto.UploadRequest requestDto = unitDto.getUploadRequest();
        Course course = courseJpaRepository.findById(unitDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(unitDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        creatorService.identifyCourseOwner(course, member);

        Unit unit = requestDto.toEntity(course);
        return new SingleResponseDto<>(unitJpaRepository.save(unit).getId());
    }

    /**
     * 강의를 재생합니다.
     *
     * @param unitDto 재생할 강의 아이디넘버
     * @return 강의 세부 정보
     */
    public UnitDto.Response playUnit(UnitDto unitDto) {
        Unit unit = unitJpaRepository.findById(unitDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(unitDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, unit.getCourse())) {
            throw new CAccessDeniedException();
        }
        return new UnitDto.Response(unit);
    }
}
