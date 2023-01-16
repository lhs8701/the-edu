package joeuncamp.dabombackend.domain.course.respository;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import joeuncamp.dabombackend.global.constant.CategoryType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CoursePagingTest {

    @Autowired
    CourseJpaRepository courseJpaRepository;

    @Autowired
    CreatorProfileJpaRepository creatorProfileJpaRepository;

    static Course c1;
    static Course c2;
    static Course c3;
    static Course c4;
    static Course c5;
    static CreatorProfile creator;

    @BeforeAll
    static void init() {
        creator = CreatorProfile.builder().build();
        c1 = Course.builder()
                .title("apple")
                .category(CategoryType.BACK_END)
                .creatorProfile(creator)
                .build();
        c2 = Course.builder()
                .title("dog")
                .category(CategoryType.BACK_END)
                .creatorProfile(creator)
                .build();
        c3 = Course.builder()
                .title("banana")
                .category(CategoryType.BACK_END)
                .creatorProfile(creator)
                .build();
        c4 = Course.builder()
                .title("dog")
                .category(CategoryType.BACK_END)
                .creatorProfile(creator)
                .build();
        c5 = Course.builder()
                .title("cat")
                .category(CategoryType.BACK_END)
                .creatorProfile(creator)
                .build();
    }

    @Test
    @DisplayName("강좌를 페이지 단위로 조회한다.")
    void 강좌를_페이지_단위로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(1, 2);
        creatorProfileJpaRepository.save(creator);
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);
        courseJpaRepository.save(c5);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategory(CategoryType.BACK_END, pageable);

        // then
        assertThat(pages.getNumber()).isEqualTo(1);
        assertThat(pages.getContent().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("강좌를 생성일 순으로 조회한다.")
    void 강좌를_생성일_순으로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdTime").ascending());
        creatorProfileJpaRepository.save(creator);
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);
        courseJpaRepository.save(c5);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategory(CategoryType.BACK_END, pageable);
        List<Course> courses = pages.getContent();

        // then
        assertThat(courses).containsExactly(c1, c2, c3, c4, c5);
        assertThat(pages.getContent().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("강좌를 최근 생성일 순으로 조회한다.")
    void 강좌를_최근_생성일_순으로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdTime"));
        creatorProfileJpaRepository.save(creator);
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);
        courseJpaRepository.save(c5);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategory(CategoryType.BACK_END, pageable);
        List<Course> courses = pages.getContent();

        // then
        assertThat(courses).containsExactly(c5, c4, c3, c2, c1);
        assertThat(pages.getContent().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("강좌를 제목 순, 최근 생성일 순으로 조회한다.")
    void 강좌를_제목_순_최근_생성일_순으로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "title").and(Sort.by(Sort.Direction.DESC, "createdTime")));
        creatorProfileJpaRepository.save(creator);
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);
        courseJpaRepository.save(c5);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategory(CategoryType.BACK_END, pageable);
        List<Course> courses = pages.getContent();

        // then
        assertThat(courses).containsExactly(c1, c3, c5, c4, c2);
        assertThat(pages.getContent().size()).isEqualTo(5);
    }
}
