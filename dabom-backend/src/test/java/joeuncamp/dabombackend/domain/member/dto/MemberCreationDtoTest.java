package joeuncamp.dabombackend.domain.member.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import joeuncamp.dabombackend.domain.auth.basic.dto.SignupRequestDto;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.ValidationMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class MemberCreationDtoTest {

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
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .account("test")
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build();
        // when
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo(ValidationMessage.NOT_VALID_EMAIL);
        });
    }

    @ParameterizedTest
    @CsvSource({"Ab1!", "Abcdefghijklmnop1234567890!", "abcd1234", "Abcd1234", "abcd1234"})
    @DisplayName("비밀번호가 형식에 맞지 않으면 예외가 발생한다.")
    void 비밀번호가_형식에_맞지_않으면_예외가_발생한다(String invalidPassword) {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .account( ExampleValue.Member.ACCOUNT)
                .password(invalidPassword)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build();
        // when
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo(ValidationMessage.NOT_VALID_PASSWORD);
        });
    }

    @ParameterizedTest
    @CsvSource({"가", "가나다라마바사아자차카타파하가나다라마바사아자차카타파하"})
    @DisplayName("닉네임의 글자수가 2~16자가 아닐 경우 예외가 발생한다.")
    void 닉네임의_글자수가_형식에_맞지_않을_경우_예외가_발생한다(String invalidNickname) {
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .account( ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(invalidNickname)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build();
        // when
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo(ValidationMessage.NOT_VALID_NICKNAME);
        });
    }

    @Test
    @DisplayName("전화번호가 형식에 맞지 않으면 예외가 발생한다.")
    void 전화번호가_형식에_맞지_않으면_예외가_발생한다() {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .account( ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile("0104564546")
                .birthDate(ExampleValue.Member.BIRTH_DATE)
                .build();

        // when
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo(ValidationMessage.NOT_VALID_MOBILE);
        });
    }

    @Test
    @DisplayName("생년월일이 형식에 맞지 않으면 예외가 발생한다.")
    void 생년월일이_형식에_맞지_않으면_예외가_발생한다() {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .account( ExampleValue.Member.ACCOUNT)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .mobile(ExampleValue.Member.MOBILE)
                .birthDate("19990311")
                .build();

        // when
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo(ValidationMessage.NOT_VALID_BIRTH_DATE);
        });
    }
}
