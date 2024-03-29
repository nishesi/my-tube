package ru.itis.nishesi.MyTube.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itis.nishesi.MyTube.validation.validators.NotEmptyFileValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {NotEmptyFileValidator.class})
public @interface NotEmptyFile {
    String message() default "{jakarta.validation.constraints.NotEmpty.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
