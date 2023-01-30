package joeuncamp.dabombackend.domain.course.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import org.junit.jupiter.api.*;
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
        CourseDto.CreationRequest dto = CourseDto.CreationRequest.builder()
                .title(ExampleValue.Course.TITLE)
                .description(ExampleValue.Course.DESCRIPTION)
                .category(ExampleValue.Course.CATEGORY)
                .price(-1L)
                .build();

        // when
        Set<ConstraintViolation<CourseDto.CreationRequest>> violations = validator.validate(dto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(System.out::println);
    }

    @Test
    @Disabled
    @DisplayName("유효한 카테고리 값이 아닐 경우 예외가 발생한다.")
    void 유효한_카테고리_값이_아닐_경우_예외가_발생한다() {
        // given
        CourseDto.CreationRequest dto = CourseDto.CreationRequest.builder()
                .title(ExampleValue.Course.TITLE)
                .description(ExampleValue.Course.DESCRIPTION)
                .category("테스트")
                .price(ExampleValue.Course.PRICE)
                .build();

        // when
        Set<ConstraintViolation<CourseDto.CreationRequest>> violations = validator.validate(dto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(System.out::println);
    }
}
