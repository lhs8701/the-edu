package joeuncamp.dabombackend.domain.course.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.dto.*;
import joeuncamp.dabombackend.domain.course.entity.Chapter;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.ChapterJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.player.record.service.RecordService;
import joeuncamp.dabombackend.domain.player.record.service.ViewChecker;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CurriculumService {
    private final ChapterJpaRepository chapterJpaRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollService enrollService;
    private final ViewChecker viewChecker;
    private final RecordService recordService;


    /**
     * 커리큘럼을 생성합니다.
     * 챕터를 생성하거나, 강의 간 순서를 바꿀 수 있습니다.
     *
     * @param requestDto dto
     */
    public void makeCurriculum(CurriculumDto.CreateRequest requestDto) {
        int sequence = 1;
        List<CurriculumDto.ChapterRequest> chapters = requestDto.getChapters();
        chapterJpaRepository.deleteByCourseId(requestDto.getCourseId());
        for (CurriculumDto.ChapterRequest chapterRequest : chapters) {
            Chapter chapter = joeuncamp.dabombackend.domain.course.entity.Chapter.builder()
                    .title(chapterRequest.getTitle())
                    .sequence(sequence++)
                    .courseId(requestDto.getCourseId())
                    .build();
            chapterJpaRepository.save(chapter);
            setChapter(chapter, chapterRequest.getUnits());
        }
    }

    private void setChapter(Chapter chapter, List<CurriculumDto.UnitRequest> units) {
        int sequence = 1;
        for (CurriculumDto.UnitRequest unitRequest : units) {
            Unit unit = unitJpaRepository.findById(unitRequest.getUnitId()).orElseThrow(CResourceNotFoundException::new);
            unit.setSequence(sequence++);
            unit.setChapter(chapter);
            unitJpaRepository.save(unit);
        }
    }

    /**
     * 강좌의 기본 커리큘럼을 생성합니다.
     *
     * @param course 강좌
     */
    public void makeDefaultCurriculum(Course course) {
        chapterJpaRepository.deleteByCourseId(course.getId());
        Chapter defaultChapter = chapterJpaRepository.findByIsDefaultTrue().orElseThrow(CInternalServerException::new);
        log.info("{}", defaultChapter);
        List<Unit> units = course.getUnitList();
        int sequence = 1;
        for (Unit unit : units) {
            unit.setChapter(defaultChapter);
            unit.setSequence(sequence++);
            unitJpaRepository.save(unit);
        }
    }

    /**
     * 커리큘럼 상의 첫번째 강의를 샘플 강의로 반환합니다.
     *
     * @param course 강좌
     * @return 샘플 강의
     */
    public Unit getSampleUnit(Course course) {
        List<Unit> units = unitJpaRepository.findByCourseOrderBySequence(course);
        if (units.size() == 0) {
            return null;
        }
        return units.get(0);
    }

    /**
     * 강좌의 커리큘럼을 조회합니다.
     *
     * @param requestDto 강좌
     * @return 커리큘럼 정보
     */
    public CurriculumDto.Response getCurriculum(CurriculumDto.GetRequest requestDto) {
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        List<Chapter> chapters = chapterJpaRepository.findByCourseId(course.getId());
        List<ChapterResponse> chapterDto = new ArrayList<>();
        for (Chapter chapter : chapters) {
            List<UnitResponse> unitDto = getUnitDto(chapter);
            chapterDto.add(new ChapterResponse(chapter, unitDto));
        }
        return new CurriculumDto.Response(chapterDto);
    }

    /**
     * 강좌의 커리큘럼과 함께 완강 여부를 조회합니다.
     * 강좌 수강정보도 함께 조회합니다.
     *
     * @param requestDto 강좌, 회원
     * @return 강좌의 커리큘럼
     */
    public CurriculumDto.Response getCurriculumWithStatus(CurriculumDto.StatusRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        if (!enrollService.doesEnrolled(member, course)) {
            throw new CAccessDeniedException("수강 등록한 회원만 커리큘럼(강좌 진척도)을 조회할 수 있습니다.");
        }
        List<Unit> completedUnits = viewChecker.getCompletedUnit(member, course);
        List<Chapter> chapters = chapterJpaRepository.findByCourseId(course.getId());
        List<ChapterResponse> chapterDto = new ArrayList<>();
        for (Chapter chapter : chapters) {
            List<UnitResponse> unitDto = getUnitInfo(chapter, completedUnits);
            chapterDto.add(new ChapterResponse(chapter, unitDto));
        }
        NextUnitInfo nextUnitInfo = recordService.getNextUnitInfo(member, course);
        CourseDto.StatusResponse courseStatus = new CourseDto.StatusResponse(course, completedUnits.size(), nextUnitInfo);
        return new CurriculumDto.Response(chapterDto, courseStatus);
    }
    private List<UnitResponse> getUnitDto(Chapter chapter) {
        List<UnitResponse> unitDto = new ArrayList<>();
        List<Unit> units = chapter.getUnits();
        for (Unit unit : units) {
            unitDto.add(new UnitDefaultResponse(unit));
        }
        return unitDto;
    }

    private List<UnitResponse> getUnitInfo(Chapter chapter, List<Unit> completedUnits) {
        List<UnitResponse> unitDto = new ArrayList<>();
        List<Unit> units = chapter.getUnits();
        for (Unit unit : units) {
            unitDto.add(new UnitStatusResponse(unit, completedUnits.contains(unit)));
        }
        return unitDto;
    }
}
