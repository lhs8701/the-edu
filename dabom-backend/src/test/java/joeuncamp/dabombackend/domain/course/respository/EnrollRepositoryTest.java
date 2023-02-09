package joeuncamp.dabombackend.domain.course.respository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
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
public class EnrollRepositoryTest {
    @Autowired
    EnrollJpaRepository enrollJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    CourseJpaRepository courseJpaRepository;

    @Test
    @DisplayName("등록 정보가 저장되면, 회원과 강좌에도 반영된다.")
    void 등록_정보가_저장되면_회원과_강좌에도_반영된다() {
        // given
        Member member = Member.builder().build();
        memberJpaRepository.save(member);

        Course course = Course.builder().build();
        courseJpaRepository.save(course);

        Enroll enroll = Enroll.builder()
                .member(member)
                .course(course)
                .build();

        // when
        enrollJpaRepository.save(enroll);

        // then
        Assertions.assertThat(member.getEnrollList()).contains(enroll);
        Assertions.assertThat(course.getEnrollList()).contains(enroll);
    }
}
