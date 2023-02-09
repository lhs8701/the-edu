package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.entity.Chapter;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.ChapterJpaRepository;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterJpaRepository chapterJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    public void setUnitToLastChapter(Unit unit, Long courseId) {
        Chapter lastChapter = chapterJpaRepository.findTop1ByCourseIdOrderBySequenceDesc(courseId).orElseThrow(CInternalServerException::new);
        log.info("{}", lastChapter);
        int lastSequence = getLastSequenceOfChapter(lastChapter);
        unit.setChapter(lastChapter);
        unit.setSequence(lastSequence + 1);
    }

    private int getLastSequenceOfChapter(Chapter lastChapter) {
        Optional<Unit> lastUnit = unitJpaRepository.findTop1ByChapterOrderBySequenceDesc(lastChapter);
        if (lastUnit.isEmpty()){
            return 0;
        }
        return lastUnit.get().getSequence();
    }
}
