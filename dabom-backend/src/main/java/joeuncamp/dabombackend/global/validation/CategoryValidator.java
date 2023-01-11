package joeuncamp.dabombackend.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import joeuncamp.dabombackend.global.constant.CategoryType;

public class CategoryValidator implements ConstraintValidator<Category, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CategoryType.findByTitle(value) != CategoryType.EMPTY;
    }
}
