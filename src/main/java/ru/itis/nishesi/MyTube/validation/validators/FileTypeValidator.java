package ru.itis.nishesi.MyTube.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.nishesi.MyTube.validation.constraints.FileType;

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
