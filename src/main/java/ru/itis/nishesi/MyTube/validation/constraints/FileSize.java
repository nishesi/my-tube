package ru.itis.nishesi.MyTube.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itis.nishesi.MyTube.validation.validators.FileSizeValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {FileSizeValidator.class})
public @interface FileSize {

    String message() default "File size must be between {min} and {max}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    long min() default 0;
    long max() default 1024*1024*8;
}
