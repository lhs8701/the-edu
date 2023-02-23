package joeuncamp.dabombackend.domain.unit.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.entity.Chapter;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.service.ChapterService;
import joeuncamp.dabombackend.domain.course.service.EnrollService;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.creator.service.CreatorService;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitService {
    private final UnitJpaRepository unitJpaRepository;
    private final CreatorService creatorService;
    private final CourseJpaRepository courseJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollService enrollService;
    private final ChapterService chapterService;

    /**
     * 강의를 업로드합니다.
     *
     * @param requestDto 강의 업로드 DTO
     * @return 생성된 강의 아이디
     */
    public Long uploadUnit(UnitDto.UploadRequest requestDto) {
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        creatorService.identifyCourseOwner(course, member.getCreatorProfile());
        Unit unit = requestDto.toEntity(course);
        chapterService.setUnitToLastChapter(unit, course.getId());
        return unitJpaRepository.save(unit).getId();
    }

    /**
     * 강의 재생을 위한 세부정보를 조회합니다.
     *
     * @param requestDto 재생할 강의 아이디넘버
     * @return 강의 세부 정보
     */
    public UnitDto.Response playUnit(UnitDto.PlayRequest requestDto) {
        Unit unit = unitJpaRepository.findById(requestDto.getUnitId()).orElseThrow(CResourceNotFoundException::new);
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, unit.getCourse())) {
            throw new CAccessDeniedException("등록하지 않은 강좌입니다.");
        }
        return new UnitDto.Response(unit);
    }
}
