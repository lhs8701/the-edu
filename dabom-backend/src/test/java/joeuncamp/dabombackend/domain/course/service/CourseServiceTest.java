package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.member.service.CreatorService;
import joeuncamp.dabombackend.domain.post.service.ReviewService;
import joeuncamp.dabombackend.global.common.PagingDto;
import joeuncamp.dabombackend.global.constant.CategoryGroup;
import joeuncamp.dabombackend.global.constant.CategoryType;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.error.exception.CCreationDeniedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @InjectMocks
    CourseService courseService;

    @Mock
    CreatorService creatorService;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    CourseJpaRepository courseJpaRepository;

    @Mock
    ReviewService reviewService;

    static Member instructor;
    static CreatorProfile creatorProfile;
    static Course course;

    @BeforeAll
    static void init() {
        instructor = Member.builder()
                .name(ExampleValue.Member.NAME)
                .build();
        creatorProfile = CreatorProfile.builder()
                .member(instructor)
                .build();
        course = Course.builder()
                .title(ExampleValue.Course.TITLE)
                .category(CategoryType.BACK_END)
                .creatorProfile(creatorProfile)
                .build();
    }

    @Test
    @DisplayName("크리에이터가 아닌 사람이 강좌를 개설할 경우 예외가 발생한다.")
    void 크리에이터가_아닌_사람이_강좌를_개설할_경우_예외가_발생한다() {
        // given
        CourseDto.CreationRequest requestDto = CourseDto.CreationRequest.builder().build();
        Member member = Member.builder().build();
        Long memberId = 1L;
        given(memberJpaRepository.findById(memberId)).willReturn(Optional.of(member));
        given(creatorService.hasCreatorProfile(member)).willReturn(false);

        // when

        // then
        Assertions.assertThatThrownBy(() -> courseService.openCourse(requestDto, memberId))
                .isInstanceOf(CCreationDeniedException.class);
    }

    @Test
    @DisplayName("강좌 단건 조회 시, 강좌 정보가 반환된다.")
    void 강좌_단건_조회_시_강좌_정보가_반환된다() {
        // given
        Long courseId = 1L;
        given(courseJpaRepository.findById(courseId)).willReturn(Optional.of(course));
        given(reviewService.calculateAverageScore(course)).willReturn(3.5);

        // when
        CourseDto.Response responseDto = courseService.getCourse(courseId);

        // then
        assertThat(responseDto.getInstructor()).isEqualTo(instructor.getName());
        assertThat(responseDto.getCategory()).isEqualTo(CategoryType.findByTitle(ExampleValue.Course.CATEGORY));
    }

    @Test
    @DisplayName("강좌 전체 조회시, 카테고리 내의 모든 강좌에 대한 정보가 반환된다.")
    void 카테고리_내의_모든_강좌_정보가_반환된다() {
        // given
        String category = ExampleValue.Course.CATEGORY;
        Pageable pageable = PageRequest.of(0, 2);
        Page<Course> page = new PageImpl<>(List.of(course));
        given(courseJpaRepository.findCourseByCategory(CategoryType.BACK_END, pageable)).willReturn(page);

        // when
        PagingDto<CourseDto.ShortResponse> responseDto = courseService.getCoursesByCategory(category, pageable);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getList().get(0).getTitle()).isEqualTo(ExampleValue.Course.TITLE);
    }

}
