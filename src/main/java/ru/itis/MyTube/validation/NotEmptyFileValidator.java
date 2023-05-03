package ru.itis.MyTube.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class NotEmptyFileValidator implements ConstraintValidator<NotEmptyFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return !value.isEmpty();
    }
}
