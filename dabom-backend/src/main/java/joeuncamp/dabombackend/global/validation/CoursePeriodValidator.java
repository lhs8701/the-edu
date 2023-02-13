package joeuncamp.dabombackend.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import joeuncamp.dabombackend.domain.order.entity.CoursePeriod;

public class CoursePeriodValidator implements ConstraintValidator<CoursePeriodValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CoursePeriod.findByName(value) != CoursePeriod.EMPTY;
    }
}
