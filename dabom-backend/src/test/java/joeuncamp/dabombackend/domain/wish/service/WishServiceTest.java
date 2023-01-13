package joeuncamp.dabombackend.domain.wish.service;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
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
    @DisplayName("찜이 되어있는 상태라면 찜을 해제한다.")
    void 찜이_되어있는_상태라면_찜을_해제한다() {
        // given
        Member member = Member.builder().build();
        Course course = Course.builder().build();
        WishRequestDto requestDto = WishRequestDto.builder()
                .memberId(1L)
                .courseId(1L)
                .build();

        Wish wish = requestDto.toEntity(member, course);
        given(memberJpaRepository.findById(1L)).willReturn(Optional.of(member));
        given(courseJpaRepository.findById(1L)).willReturn(Optional.of(course));
        given(wishJpaRepository.save(wish)).willReturn(wish);

        // when
        wishService.toggleWish(requestDto);

        // then
        assertThat(member.getWishList()).doesNotContain(wish);
    }
}
