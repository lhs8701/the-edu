package joeuncamp.dabombackend.domain.course.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class CourseCreationDtoTest {
    static ValidatorFactory factory;
    static Validator validator;

    @BeforeAll
    public static void init(){
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close(){
        factory.close();
    }

    @Test
    @DisplayName("강좌의 가격이 0보다 작으면 예외가 발생한다.")
    void 가격이_0_보다_작으면_예외가_발생한다() {
        // given
        CourseCreationRequestDto dto = CourseCreationRequestDto.builder()
                .price(-1L)
                .build();

        // when
        Set<ConstraintViolation<CourseCreationRequestDto>> violations = validator.validate(dto);

        // then
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("유효한 카테고리 값이 아닐 경우 예외가 발생한다.")
    void 유효한_카테고리_값이_아닐_경우_예외가_발생한다() {
        // given
        CourseCreationRequestDto dto = CourseCreationRequestDto.builder()
                .category("테스트")
                .build();

        // when
        Set<ConstraintViolation<CourseCreationRequestDto>> violations = validator.validate(dto);

        // then
        assertThat(violations).isNotEmpty();
    }
}
