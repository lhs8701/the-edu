package joeuncamp.dabombackend.domain.wish.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WishRepositoryTest {
    @Autowired
    WishJpaRepository wishJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    CourseJpaRepository courseJpaRepository;
    @Test
    @DisplayName("찜이 저장되면, 회원과 찜, 강좌와 찜 간에 연관관계가 맺어진다")
    void 찜이_저장되면_회원과_찜과_강좌_간에_연관관계가_맺어진다() {
        // given
        Member member = Member.builder().build();
        memberJpaRepository.save(member);

        Course course = Course.builder().build();
        courseJpaRepository.save(course);

        Wish wish = Wish.builder()
                .member(member)
                .course(course)
                .build();

        // when
        wishJpaRepository.save(wish);

        // then
        Assertions.assertThat(member.getWishList().get(0)).isEqualTo(wish);
        Assertions.assertThat(course.getWishList().get(0)).isEqualTo(wish);
    }
}
