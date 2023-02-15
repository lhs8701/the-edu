package joeuncamp.dabombackend.domain.course.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.entity.Chapter;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.ChapterJpaRepository;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterJpaRepository chapterJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    public void setUnitToLastChapter(Unit unit, Long courseId) {
        Chapter lastChapter = chapterJpaRepository.findTop1ByCourseIdOrderBySequenceDesc(courseId).orElseThrow(CInternalServerException::new);
        int lastSequence = getLastSequenceOfChapter(lastChapter);
        unit.setChapter(lastChapter);
        unit.setSequence(lastSequence + 1);
    }

    private int getLastSequenceOfChapter(Chapter lastChapter) {
        Optional<Unit> lastUnit = unitJpaRepository.findTop1ByChapterOrderBySequenceDesc(lastChapter);
        if (lastUnit.isEmpty()) {
            return 0;
        }
        return lastUnit.get().getSequence();
    }

    /**
     * 강좌의 기본 커리큘럼을 생성합니다.
     *
     * @param course 강좌
     */
    public void saveDefaultChapter(Course course) {
        Chapter chapter = Chapter.builder()
                .courseId(course.getId())
                .sequence(1)
                .title("챕터")
                .isDefault(true)
                .build();
        chapterJpaRepository.save(chapter);
    }

    /**
     * 강좌에 설정된 챕터를 모두 삭제합니다.
     * 삭제 전, 강의와 맺어진 연관관계를 모두 끊어줍니다.
     *
     * @param courseId 강좌
     */
    public void deleteByCourse(Long courseId) {
        List<Chapter> chapters = chapterJpaRepository.findByCourseId(courseId);
        for (Chapter chapter : chapters) {
            List<Unit> units = unitJpaRepository.findByChapter(chapter);
            units.forEach(Unit::removeChapter);
            chapterJpaRepository.delete(chapter);
        }
    }
}
