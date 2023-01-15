package joeuncamp.dabombackend.domain.post.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.post.entity.Review;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {
    @Autowired
    PostJpaRepository<Review> postJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    CourseJpaRepository courseJpaRepository;

    @Test
    @DisplayName("후기를 저장하고 조회한다")
    void 후기를_저장하고_조회한다() {
        // given
        Member member = Member.builder().build();
        Course course = Course.builder().build();
        Review review = Review.builder()
                .member(member)
                .course(course)
                .likes(15)
                .build();

        // when
        Review saved = postJpaRepository.save(review);
        Review found = postJpaRepository.findById(saved.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
    }

    @Test
    @DisplayName("후기를 저장하면, 작성자와 강좌와 연관관계가 맺어진다")
    void 작성자와_강좌와_연관관계가_맺어진다() {
        // given
        Member member = Member.builder().build();
        Course course = Course.builder().build();
        memberJpaRepository.save(member);
        courseJpaRepository.save(course);

        Review review = Review.builder()
                .member(member)
                .course(course)
                .likes(15)
                .build();

        // when
        Review saved = postJpaRepository.save(review);

        // then
        assertThat(saved.getMember()).isEqualTo(member);
        assertThat(saved.getCourse()).isEqualTo(course);
        assertThat(member.getPostList()).contains(saved);
        assertThat(course.getPostList()).contains(saved);
    }
}
