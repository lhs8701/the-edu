package joeuncamp.dabombackend.domain.course.respository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.domain.post.repository.ReviewJpaRepository;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import joeuncamp.dabombackend.domain.wish.repository.WishJpaRepository;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import joeuncamp.dabombackend.global.constant.CategoryType;
import org.junit.jupiter.api.*;
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

    @Autowired
    ReviewJpaRepository reviewJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    EnrollJpaRepository enrollJpaRepository;

    @Autowired
    WishJpaRepository wishJpaRepository;

    @Test
    @DisplayName("강좌를 페이지 단위로 조회한다.")
    void 강좌를_페이지_단위로_조회한다() {
        // given
        Course c1 = Course.builder().category(CategoryType.BACK_END).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).build();
        Pageable pageable = PageRequest.of(1, 2);
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategory(CategoryType.BACK_END, pageable);

        // then
        assertThat(pages.getNumber()).isEqualTo(1);
        assertThat(pages.getContent().size()).isEqualTo(2);
    }

    @Test
    @Disabled
    @DisplayName("강좌를 생성일 순으로 조회한다.")
    void 강좌를_생성일_순으로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "createdTime"));
        CreatorProfile creator = CreatorProfile.builder().build();
        Course c1 = Course.builder().category(CategoryType.BACK_END).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).build();
        creatorProfileJpaRepository.save(creator);
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategory(CategoryType.BACK_END, pageable);
        List<Course> courses = pages.getContent();

        // then
        assertThat(courses).containsExactly(c1, c2, c3, c4);
        assertThat(pages.getContent().size()).isEqualTo(5);
    }

    @Test
    @Disabled
    @DisplayName("강좌를 최근 생성일 순으로 조회한다.")
    void 강좌를_최근_생성일_순으로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdTime"));
        Course c1 = Course.builder().category(CategoryType.BACK_END).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).build();
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategory(CategoryType.BACK_END, pageable);
        List<Course> courses = pages.getContent();

        // then
        assertThat(courses).containsExactly(c4, c3, c2, c1);
        assertThat(pages.getContent().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("강좌를 제목 순으로 조회한다.")
    void 강좌를_제목_순으로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "title"));
        Course c1 = Course.builder().category(CategoryType.BACK_END).title("b").build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).title("a").build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).title("d").build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).title("c").build();
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategory(CategoryType.BACK_END, pageable);
        List<Course> courses = pages.getContent();

        // then
        assertThat(courses).containsExactly(c2, c1, c4, c3);
        assertThat(pages.getContent().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("강좌를 평균 평점(DESC) 순으로 조회한다.")
    void 강좌를_평균_평점_DESC_순으로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5);
        Member member = Member.builder().build();
        memberJpaRepository.save(member);
        Course c1 = Course.builder().category(CategoryType.BACK_END).title("1").build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).title("2").build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).title("3").build();
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);

        Review r1 = Review.builder().course(c1).score(2).member(member).build();
        Review r2 = Review.builder().course(c2).score(2).member(member).build();
        Review r3 = Review.builder().course(c3).score(3).member(member).build();
        Review r4 = Review.builder().course(c2).score(5).member(member).build();
        reviewJpaRepository.save(r1);
        reviewJpaRepository.save(r2);
        reviewJpaRepository.save(r3);
        reviewJpaRepository.save(r4);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategoryOrderByScore(CategoryType.BACK_END, pageable);
        List<Course> courses = pages.getContent();

        // then
        assertThat(courses).containsExactly(c2, c3, c1);
        assertThat(pages.getContent().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("강좌를 수강생(DESC) 순으로 조회한다.")
    void 강좌를_수강생_순으로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5);
        Member member = Member.builder().build();
        memberJpaRepository.save(member);
        Course c1 = Course.builder().category(CategoryType.BACK_END).title("1").build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).title("2").build();
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);

        Enroll e1 = Enroll.builder().member(member).course(c1).build();
        Enroll e2 = Enroll.builder().member(member).course(c2).build();
        Enroll e3 = Enroll.builder().member(member).course(c2).build();
        enrollJpaRepository.save(e1);
        enrollJpaRepository.save(e2);
        enrollJpaRepository.save(e3);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategoryOrderByEnrollCount(CategoryType.BACK_END, pageable);
        List<Course> courses = pages.getContent();

        // then
        assertThat(courses).containsExactly(c2, c1);
        assertThat(pages.getContent().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("강좌를 찜(DESC) 순으로 조회한다.")
    void 강좌를_찜_순으로_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 5);
        Member member = Member.builder().build();
        memberJpaRepository.save(member);
        Course c1 = Course.builder().category(CategoryType.BACK_END).title("1").build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).title("2").build();
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);

        Wish w1 = Wish.builder().member(member).course(c1).build();
        Wish w2 = Wish.builder().member(member).course(c2).build();
        Wish w3 = Wish.builder().member(member).course(c2).build();
        wishJpaRepository.save(w1);
        wishJpaRepository.save(w2);
        wishJpaRepository.save(w3);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategoryOrderByWishCount(CategoryType.BACK_END, pageable);
        List<Course> courses = pages.getContent();

        // then
        assertThat(courses).containsExactly(c2, c1);
        assertThat(pages.getContent().size()).isEqualTo(2);
    }


    @Test
    @DisplayName("강사명이나 제목에 키워드가 포함된 강좌를 모두 조회한다.")
    void 강사명이나_제목에_키워드가_포함된_강좌를_모두_조회한다() {
        String keyword = "철수";
        Member m1 = Member.builder().name("김철수").build();
        Member m2 = Member.builder().name("이철수").build();
        Member m3 = Member.builder().name("정민수").build();
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        memberJpaRepository.save(m3);
        CreatorProfile cr1 = CreatorProfile.builder().member(m1).build();
        CreatorProfile cr2 = CreatorProfile.builder().member(m2).build();
        CreatorProfile cr3 = CreatorProfile.builder().member(m3).build();
        creatorProfileJpaRepository.save(cr1);
        creatorProfileJpaRepository.save(cr2);
        creatorProfileJpaRepository.save(cr3);
        Pageable pageable = PageRequest.of(0, 5);
        Course c1 = Course.builder().category(CategoryType.BACK_END).title("c1 철수").creatorProfile(cr1).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).title("c2 test").creatorProfile(cr1).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).title("c3 test").creatorProfile(cr2).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).title("c4 test").creatorProfile(cr3).build();
        Course c5 = Course.builder().category(CategoryType.BACK_END).title("c5 철수").build();
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);
        courseJpaRepository.save(c5);

        // when
        Page<Course> pages = courseJpaRepository.findAllByKeyword(keyword, pageable);

        // then
        System.out.println("pages = " + pages.getContent());
        assertThat(pages.getContent()).containsExactlyInAnyOrder(c1, c2, c3, c5);
    }
}
