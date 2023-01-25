package joeuncamp.dabombackend.domain.wish.service;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.wish.dto.WishDto;
import joeuncamp.dabombackend.domain.wish.dto.WishRequestDto;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import joeuncamp.dabombackend.domain.wish.repository.WishJpaRepository;
import org.assertj.core.api.Assertions;
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
public class WishServiceTest {
    @InjectMocks
    WishService wishService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    CourseJpaRepository courseJpaRepository;

    @Mock
    WishJpaRepository wishJpaRepository;


    @Test
    @DisplayName("해당 강좌를 찜한 경우 true를 반환한다.")
    void 해당_강좌를_찜한_경우_true를_반환한다() {
        // given
        Member member = Member.builder().build();
        Course course = Course.builder().build();
        Wish wish = Wish.builder().course(course).member(member).build();
        WishDto.Request requestDto = WishDto.Request.builder()
                .courseId(1L)
                .memberId(1L)
                .build();

        given(memberJpaRepository.findById(1L)).willReturn(Optional.of(member));
        given(courseJpaRepository.findById(1L)).willReturn(Optional.of(course));
        given(wishJpaRepository.findByMemberAndCourse(member, course)).willReturn(Optional.ofNullable(wish));

        // when
        boolean result = wishService.checkWish(requestDto);

        // then
        Assertions.assertThat(result).isEqualTo(true);
    }

}
