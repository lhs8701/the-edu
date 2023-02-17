package joeuncamp.dabombackend.domain.course.respository;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.BeforeAll;
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

    static CourseDto.CreationRequest requestDto;

    @BeforeAll
    static void init(){
        requestDto = CourseDto.CreationRequest.builder()
                .title(ExampleValue.Course.TITLE)
                .description(ExampleValue.Course.DESCRIPTION)
                .category(ExampleValue.Course.CATEGORY)
                .build();
    }

    @Test
    @DisplayName("DB에 강좌를 저장하고 조회한다.")
    void DB에_강좌를_저장하고_조회한다() {
        // given
        Course course = Course.builder().title(ExampleValue.Course.TITLE).build();

        // when
        Long saved = courseJpaRepository.save(course).getId();

        // then
        Optional<Course> found = courseJpaRepository.findById(saved);
        assertThat(found.isPresent()).isEqualTo(true);
        assertThat(found.get().getTitle()).isEqualTo(ExampleValue.Course.TITLE);
    }

    @Test
    @DisplayName("DB에서 강좌를 삭제한다.")
    void DB에서_강좌를_삭제한다() {
        // given
        Course course = Course.builder().title(ExampleValue.Course.TITLE).build();
        Long saved = courseJpaRepository.save(course).getId();

        // when
        courseJpaRepository.deleteById(saved);

        // then
        Optional<Course> found = courseJpaRepository.findById(saved);
        assertThat(found.isEmpty()).isEqualTo(true);
    }
}
