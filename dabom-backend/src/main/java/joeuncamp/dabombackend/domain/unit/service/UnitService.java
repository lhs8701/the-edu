package joeuncamp.dabombackend.domain.unit.service;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitJpaRepository unitJpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    public Long uploadUnit(UnitDto.UploadRequest requestDto){
        Course course = courseJpaRepository.findById(requestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        Unit unit = requestDto.toEntity(course);
        return unitJpaRepository.save(unit).getId();
    }
}
