package joeuncamp.dabombackend.domain.course.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.dto.CurriculumDto;
import joeuncamp.dabombackend.domain.course.dto.MyCourseDto;
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
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
     * 챕터를 생성하거나, 강의간 순서를 바꿀 수 있습니다.
     *
     * @param requestDto dto
     */
    @Transactional
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
     * 강좌의 커리큘럼을 조회합니다.
     *
     * @param requestDto 강좌
     * @return 커리큘럼 정보
     */
    public CurriculumDto.Response getCurriculum(CurriculumDto.GetRequest requestDto) {
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        List<Unit> units = unitJpaRepository.findByCourseOrderBySequence(course);
        if (units.size() == 0) {
            return null;
        }
        List<CurriculumDto.ChapterResponse> chapterResponses = getChapters(units);
        return new CurriculumDto.Response(chapterResponses);
    }

    private List<CurriculumDto.ChapterResponse> getChapters(List<Unit> units) {
        List<CurriculumDto.ChapterResponse> chapterResponseList = new ArrayList<>();
        CurriculumDto.ChapterResponse chapterResponse = new CurriculumDto.ChapterResponse();
        chapterResponse.setTitle(units.get(0).getChapter().getTitle());
        Chapter prev = units.get(0).getChapter();
        for (Unit unit : units) {
            if (!unit.getChapter().equals(prev)) {
                chapterResponseList.add(chapterResponse);
                chapterResponse = new CurriculumDto.ChapterResponse();
                chapterResponse.setTitle(unit.getChapter().getTitle());
                prev = unit.getChapter();
            }
            chapterResponse.getUnits().add(new CurriculumDto.UnitResponse(unit));
        }
        chapterResponseList.add(chapterResponse);
        return chapterResponseList;
    }

    /**
     * 강좌의 커리큘럼과 함께 완강 여부를 조회합니다.
     *
     * @param requestDto 강좌, 회원
     * @return 강좌의 커리큘럼
     */
    public CurriculumDto.StatusResponse getCurriculumWithStatus(CurriculumDto.StatusRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        List<Unit> units = unitJpaRepository.findByCourseOrderBySequence(course);
        if (units.size() == 0) {
            return null;
        }
        if (!enrollService.doesEnrolled(member, course)) {
            throw new CAccessDeniedException();
        }
        List<Unit> completedUnits = viewChecker.getCompletedUnit(member, course);
        MyCourseDto.Response courseStatus = new MyCourseDto.Response(course, completedUnits.size(), recordService.getNextUnitInfo(member, course));
        List<CurriculumDto.ChapterStatusResponse> chapters = getChaptersWithStatus(units, completedUnits);
        return new CurriculumDto.StatusResponse(courseStatus, chapters);
    }

    private List<CurriculumDto.ChapterStatusResponse> getChaptersWithStatus(List<Unit> units, List<Unit> completedUnits) {
        CurriculumDto.ChapterStatusResponse chapterResponse = new CurriculumDto.ChapterStatusResponse();
        chapterResponse.setTitle(units.get(0).getChapter().getTitle());
        Chapter prev = units.get(0).getChapter();
        List<CurriculumDto.ChapterStatusResponse> chapterList = new ArrayList<>();
        for (Unit unit : units) {
            if (!unit.getChapter().equals(prev)) {
                chapterList.add(chapterResponse);
                chapterResponse = new CurriculumDto.ChapterStatusResponse();
                chapterResponse.setTitle(unit.getChapter().getTitle());
                prev = unit.getChapter();
            }
            chapterResponse.getUnits().add(new CurriculumDto.UnitStatusResponse(unit, completedUnits.contains(unit)));
        }
        chapterList.add(chapterResponse);
        return chapterList;
    }
}
