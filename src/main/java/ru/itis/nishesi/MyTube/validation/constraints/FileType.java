package ru.itis.nishesi.MyTube.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itis.nishesi.MyTube.validation.validators.FileTypeValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {FileTypeValidator.class})
public @interface FileType {

    String message() default "File type is not acceptable. Possible types: {acceptableTypes}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * If empty, then any file type is valid.
     * @return acceptable types.
     */
    String[] acceptableTypes() default {};
}
