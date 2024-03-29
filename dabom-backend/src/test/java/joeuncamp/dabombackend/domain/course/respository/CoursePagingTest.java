package joeuncamp.dabombackend.domain.course.respository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.creator.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.domain.post.repository.ReviewJpaRepository;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import joeuncamp.dabombackend.domain.wish.repository.WishJpaRepository;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.util.tossapi.dto.MemberPrivacy;
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
        Course c1 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Pageable pageable = PageRequest.of(1, 2);
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategoryAndActiveIsTrue(CategoryType.BACK_END, pageable);

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
        Course c1 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        creatorProfileJpaRepository.save(creator);
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategoryAndActiveIsTrue(CategoryType.BACK_END, pageable);
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
        Course c1 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).build();
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategoryAndActiveIsTrue(CategoryType.BACK_END, pageable);
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
        Course c1 = Course.builder().category(CategoryType.BACK_END).title("b").locked(false).active(true).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).title("a").locked(false).active(true).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).title("d").locked(false).active(true).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).title("c").locked(false).active(true).build();
        courseJpaRepository.save(c1);
        courseJpaRepository.save(c2);
        courseJpaRepository.save(c3);
        courseJpaRepository.save(c4);

        // when
        Page<Course> pages = courseJpaRepository.findCourseByCategoryAndActiveIsTrue(CategoryType.BACK_END, pageable);
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
        Course c1 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).title("1").build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).title("2").build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).title("3").build();
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
        Course c1 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).title("1").build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).title("2").build();
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
        Course c1 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).title("1").build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).locked(false).active(true).title("2").build();
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
        Member m1 = Member.builder().memberPrivacy(MemberPrivacy.builder().name("김철수").build()).build();
        Member m2 = Member.builder().memberPrivacy(MemberPrivacy.builder().name("이철수").build()).build();
        Member m3 = Member.builder().memberPrivacy(MemberPrivacy.builder().name("정민수").build()).build();
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        memberJpaRepository.save(m3);
        CreatorProfile cr1 = CreatorProfile.builder().member(m1).build();
        CreatorProfile cr2 = CreatorProfile.builder().member(m2).build();
        CreatorProfile cr3 = CreatorProfile.builder().member(m3).build();
        creatorProfileJpaRepository.save(cr1);
        creatorProfileJpaRepository.save(cr2);
        creatorProfileJpaRepository.save(cr3);
        Pageable pageable = PageRequest.of(0, 10);
        Course c1 = Course.builder().category(CategoryType.BACK_END).title("c1 철수").locked(false).active(true).creatorProfile(cr1).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).title("c2 test").locked(false).active(true).creatorProfile(cr1).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).title("c3 test").locked(false).active(true).creatorProfile(cr2).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).title("c4 test").locked(false).active(true).creatorProfile(cr3).build();
        Course c5 = Course.builder().category(CategoryType.BACK_END).title("c5 철수").locked(false).active(true).creatorProfile(cr3).build();
        courseJpaRepository.saveAll(List.of(c1, c2, c3, c4, c5));

        // when
        Page<Course> pages = courseJpaRepository.findAllByKeyword(keyword, pageable);

        // then
        System.out.println("pages = " + pages.getContent());
        assertThat(pages.getContent()).containsExactlyInAnyOrder(c1, c2, c3, c5);
    }


    @Test
    @DisplayName("검색어 앞뒤의 공백을 제거한다.")
    void 검색어_앞뒤의_공백을_제거한다() {
        // given
        String keyword = "   start  ";

        // when
        String result = keyword.trim();

        // then
        assertThat(result).isEqualTo("start");
    }

    @Test
    @DisplayName("일주일 간 등록자 수가 많은 순으로 강좌를 정렬한다.")
    void 일주일_간_등록자_수가_많은_순으로_강좌를_정렬한다() {
        // given
        Member m1 = Member.builder().memberPrivacy(MemberPrivacy.builder().name("김철수").build()).build();
        memberJpaRepository.save(m1);
        Course c1 = Course.builder().category(CategoryType.BACK_END).title("c1").locked(false).active(true).build();
        Course c2 = Course.builder().category(CategoryType.BACK_END).title("c2").locked(false).active(true).build();
        Course c3 = Course.builder().category(CategoryType.BACK_END).title("c3").locked(false).active(true).build();
        Course c4 = Course.builder().category(CategoryType.BACK_END).title("c4").locked(false).active(true).build();
        Course c5 = Course.builder().category(CategoryType.FRONT_END).title("c5").locked(false).active(true).build();
        courseJpaRepository.saveAll(List.of(c1,c2,c3,c4,c5));
        Enroll e1 = Enroll.builder().course(c1).member(m1).build();
        Enroll e2 = Enroll.builder().course(c1).member(m1).build();
        Enroll e3 = Enroll.builder().course(c2).member(m1).build();
        Enroll e4 = Enroll.builder().course(c3).member(m1).build();
        Enroll e5 = Enroll.builder().course(c3).member(m1).build();
        Enroll e6 = Enroll.builder().course(c3).member(m1).build();
        enrollJpaRepository.saveAll(List.of(e1, e2, e3, e4, e5, e6));
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Course> pages = courseJpaRepository.findByEnrolledCountFromWeek(CategoryType.BACK_END);

        // then
        System.out.println("pages = " + pages.getContent());
        assertThat(pages.getContent()).containsExactlyInAnyOrder(c3, c1, c2);
    }

}
