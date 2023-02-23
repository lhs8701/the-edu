package joeuncamp.dabombackend.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<CategoryValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return true;
//        return CategoryType.findByTitle(value) != CategoryType.EMPTY;
    }
}
