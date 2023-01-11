package joeuncamp.dabombackend.global.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {CategoryValidator.class})
public @interface Category {
}
