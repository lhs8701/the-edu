package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.course.dto.MyCourseShortResponseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    EnrollJpaRepository enrollJpaRepository;

    @Test
    @DisplayName("회원이 수강 중인 모든 강좌의 목록을 반환한다.")
    void 수강_중인_모든_강좌의_목록을_반환한다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .name(ExampleValue.Member.NAME)
                .build();
        CreatorProfile creatorProfile = CreatorProfile.builder()
                .member(member)
                .build();
        Course course = Course.builder()
                .id(1L)
                .creatorProfile(creatorProfile)
                .build();

        Enroll enroll = Enroll.builder()
                .course(course)
                .member(member)
                .build();
        List<Enroll> enrolls = List.of(enroll);

        given(memberJpaRepository.findById(1L)).willReturn(Optional.of(member));
        given(enrollJpaRepository.findAllByMember(member)).willReturn(enrolls);

        // when
        List<MyCourseShortResponseDto> responseDto = studentService.getMyCourses(1L);
        // then
        assertThat(responseDto.get(0).getCourseId()).isEqualTo(1L);
    }
}