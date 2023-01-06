package joeuncamp.dabombackend.domain.member.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    @DisplayName("회원 가입 시, 계정이 이메일 형식이 아니면 예외가 발생한다.")
    void 회원_가입_시_계정이_이메일_형식이_아니면_예외가_발생한다() {
        // given
        MemberCreationRequestDto dto = new MemberCreationRequestDto("test", "test", "010-1234-5678", "test", "test");

        // when
        Set<ConstraintViolation<MemberCreationRequestDto>> violations = validator.validate(dto);

        // then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("아이디는 이메일 형식이어야 합니다.");
        });
    }
}
