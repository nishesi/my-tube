package ru.itis.MyTube.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, CharSequence> {
    @Override
    public boolean isValid(CharSequence s, ConstraintValidatorContext cxt) {
        if (s == null) return true;

        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i)))
                return true;
        }
        return false;
    }
}
