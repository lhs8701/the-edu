package joeuncamp.dabombackend.domain.member.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class MemberDtoTest {

    static ValidatorFactory factory;
    static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close() {
        factory.close();
    }

    @Test
    @DisplayName("계정이 이메일 형식이 아니면 예외가 발생한다.")
    void 계정이_이메일_형식이_아니면_예외가_발생한다() {
        // given
        MemberCreationRequestDto memberCreationRequestDto = new MemberCreationRequestDto(
                "test",
                ExampleValue.Member.PASSWORD,
                ExampleValue.Member.NICKNAME,
                ExampleValue.Member.MOBILE,
                ExampleValue.Member.BIRTH_DATE
        );

        // when
        Set<ConstraintViolation<MemberCreationRequestDto>> violations = validator.validate(memberCreationRequestDto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo(ValidationMessage.NOT_VALID_EMAIL);
        });
    }

    @Test
    @DisplayName("생년월일이 형식에 맞지 않으면 예외가 발생한다.")
    void 생년월일이_형식에_맞지_않으면_예외가_발생한다() {
        // given
        MemberCreationRequestDto memberCreationRequestDto = new MemberCreationRequestDto(
                ExampleValue.Member.ACCOUNT,
                ExampleValue.Member.PASSWORD,
                ExampleValue.Member.NICKNAME,
                ExampleValue.Member.MOBILE,
                "19990311"
        );

        // when
        Set<ConstraintViolation<MemberCreationRequestDto>> violations = validator.validate(memberCreationRequestDto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo(ValidationMessage.NOT_VALID_BIRTH_DATE);
        });
    }
}
