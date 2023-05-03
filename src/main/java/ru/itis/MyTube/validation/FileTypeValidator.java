package ru.itis.MyTube.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {
    private String[] acceptableTypes;
    @Override
    public void initialize(FileType constraintAnnotation) {
        acceptableTypes = constraintAnnotation.acceptableTypes();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile.isEmpty()) return true;

        for (String type : acceptableTypes) {
            String inputType = multipartFile.getContentType();
            if (type.equals(inputType))
                return true;
        }
        return false;
    }
}
