package joeuncamp.dabombackend.global.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {CoursePeriodValidator.class})
public @interface CoursePeriodValidation {
    String message() default "존재하지 않는 수강권입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
