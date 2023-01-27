package joeuncamp.dabombackend.domain.unit.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UnitRepositoryTest {
    @Autowired
    UnitJpaRepository unitJpaRepository;

    @Autowired
    CourseJpaRepository courseJpaRepository;

    @Test
    @DisplayName("강의를 생성하고 저장한다.")
    void 강의를_생성하고_저장한다() {
        // given
        Course course = Course.builder().build();
        courseJpaRepository.save(course);
        Unit unit = Unit.builder().course(course).build();
        Unit saved = unitJpaRepository.save(unit);

        // when
        Optional<Unit> found = unitJpaRepository.findById(saved.getId());

        // then
        assertThat(found.isPresent()).isEqualTo(true);
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("강의를 삭제한다.")
    void 강의를_삭제한다() {
        // given
        Course course = Course.builder().build();
        courseJpaRepository.save(course);
        Unit unit = Unit.builder().course(course).build();
        Long savedId = unitJpaRepository.save(unit).getId();

        // when
        unitJpaRepository.deleteById(savedId);

        // then
        Optional<Unit> found = unitJpaRepository.findById(savedId);
        assertThat(found.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("강의와 강좌는 연관관계를 맺는다.")
    void 강의와_강좌는_연관관계를_맺는다() {
        // given
        Course course = Course.builder().build();
        courseJpaRepository.save(course);

        // when
        Unit unit = Unit.builder().course(course).build();
        unitJpaRepository.save(unit);

        // then
        assertThat(unit.getCourse()).isEqualTo(course);
        assertThat(course.getUnitList()).contains(unit);
    }
}
