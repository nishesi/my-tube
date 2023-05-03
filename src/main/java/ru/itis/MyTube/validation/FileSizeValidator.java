package ru.itis.MyTube.validation;


import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {
    private long maxSize;
    private long minSize;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        maxSize = constraintAnnotation.max();
        if (maxSize < 0) throw new ConstraintDeclarationException("max size must be >= 0");
        minSize = constraintAnnotation.min();
        if (minSize < 0) throw new ConstraintDeclarationException("min size must be >= 0");
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile.isEmpty()) return true;
        long size = multipartFile.getSize();
        return minSize <= size && size <= maxSize;
    }
}
