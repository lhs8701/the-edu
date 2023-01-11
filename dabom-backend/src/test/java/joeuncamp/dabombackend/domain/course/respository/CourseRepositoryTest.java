package joeuncamp.dabombackend.domain.course.respository;

import joeuncamp.dabombackend.domain.course.dto.CourseCreationRequestDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTest {

    @Autowired
    CourseJpaRepository courseJpaRepository;

    @Test
    @DisplayName("DB에 강좌를 저장하고 조회한다.")
    void DB에_강좌를_저장하고_조회한다() {
        // given
        CourseCreationRequestDto dto = CourseCreationRequestDto.builder()
                .title(ExampleValue.Course.TITLE)
                .description(ExampleValue.Course.DESCRIPTION)
                .category(ExampleValue.Course.CATEGORY)
                .price(ExampleValue.Course.PRICE)
                .build();

        // when
        Course course = courseJpaRepository.save(dto.toEntity());

        // then
        Course found = courseJpaRepository.findById(course.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    @DisplayName("DB에서 강좌를 삭제한다.")
    void DB에서_강좌를_삭제한다() {
        // given
        CourseCreationRequestDto dto = CourseCreationRequestDto.builder()
                .title(ExampleValue.Course.TITLE)
                .description(ExampleValue.Course.DESCRIPTION)
                .category(ExampleValue.Course.CATEGORY)
                .price(ExampleValue.Course.PRICE)
                .build();

        Course created = courseJpaRepository.save(dto.toEntity());

        // when
        courseJpaRepository.deleteById(created.getId());

        // then
        Optional<Course> found = courseJpaRepository.findById(created.getId());
        assertThat(found.isEmpty()).isEqualTo(true);
    }
}
